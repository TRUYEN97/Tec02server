package com.tec02.Service.impl.impl;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.Service.FileSystemStorageService;
import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.DownloadFileResponse;
import com.tec02.model.dto.FileVersionDto;
import com.tec02.model.dto.UploadFileRequest;
import com.tec02.model.dto.impl.VersionDto;
import com.tec02.model.dto.impl.impl.impl.FileDto;
import com.tec02.model.entity.impl.Version;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;
import com.tec02.repository.impl.fileRepo;
import com.tec02.util.ModelMapperUtil;
import com.tec02.util.Util;

import jakarta.transaction.Transactional;

@Service
public class FileService extends BaseService<FileDto, File> {

	@Autowired
	private FileSystemStorageService storageService;

	@Autowired
	private FGroupService groupService;

	@Autowired
	private fileRepo fileRepo;

	@Autowired
	private VersionFileService versionFileService;

	protected FileService(fileRepo repository) {
		super(repository, FileDto.class, File.class);
	}

	@Transactional
	public FileDto upload(UploadFileRequest entity, MultipartFile multipartFile) {
		String version = entity.getVersion();
		String fileName = entity.getName();
		String filePath = entity.getDir();
		Long fgroupID = entity.getFgroupId();
		Long fileId = entity.getId();
		Util.checkFilePath(fileName, filePath);
		filePath = Path.of(filePath).toString();
		if (fgroupID == null) {
			throw new RuntimeException("Invalid fgroupID, value == null");
		}
		if (multipartFile.isEmpty()) {
			throw new RuntimeException("Failed to store empty file " + multipartFile.getOriginalFilename());
		}
		if (Util.isInvalidVersion(version)) {
			throw new RuntimeException(String.format("version(%s) format not match x.x.x", version));
		}
		if (entity.getDescription() == null || entity.getDescription().isBlank()) {
			throw new RuntimeException(String.format("invalid description"));
		}
		FileGroup fileGroup = this.groupService.findOne(fgroupID);
		if (fileGroup == null) {
			throw new RuntimeException(String.format("Not found file-group by id = %s", fgroupID));
		}
		File file;
		if ((file = this.findOneByIdAndFileGroupId(fileId, fgroupID)) == null
				&& (file = this.findOneByFileGroupAndNameAndPath(fileGroup, fileName, filePath)) == null) {
			file = new File();
			file.setName(fileName);
			file.setPath(filePath);
			file.setFileGroup(fileGroup);
			file = update(file.getId(), file);
		}
		String dir = null;
		Version LastVersion;
		if ((LastVersion = this.versionFileService.findFirstByFileIdOrderByCreateTimeDesc(file.getId())) != null) {
			String oldVersion = LastVersion.getName();
			if (Util.isInvalidVersion(oldVersion)) {
				throw new RuntimeException(String.format("Last version(%s) format not match x.x.x", oldVersion));
			}
			if (!Util.isNewVersionGreaterThanOldVersion(version, oldVersion)) {
				throw new RuntimeException(String.format("The version must greater than %s", oldVersion));
			}
			dir = StringUtils.cleanPath(String.format("%s/%s", Path.of(LastVersion.getPath()).getParent(), version));
		} else {
			dir = StringUtils.cleanPath(String.format("%s_%s/%s/%s/%s", fileGroup.getId(), fileGroup.getName(),
					entity.getName(), System.currentTimeMillis(), version));
		}
		String localFilePath = storageService.storeFile(multipartFile, dir);
		try {
			Version verEntity = new Version();
			verEntity.setName(version);
			verEntity.setPath(localFilePath);
			verEntity.setMd5(Util.md5File(localFilePath));
			verEntity.setDescription(Util.subInLength(entity.getDescription(), 255));
			verEntity.setFile(file);
			versionFileService.updateDto(verEntity.getId(), verEntity);
			return findOneDto(file.getId());
		} catch (Exception e) {
			Path path = Path.of(localFilePath);
			path.toFile().delete();
			path.getParent().toFile().delete();
			throw e;
		}

	}

	private File findOneByIdAndFileGroupId(Long fileId, Long fgroundID) {
		if (fileId == null || fgroundID == null) {
			return null;
		}
		return fileRepo.findOneByIdAndFileGroupId(fileId, fgroundID).orElse(null);
	}

	public File findOneByFileGroupAndNameAndPath(FileGroup fileGroup, String fileName, String fileDir) {
		return fileRepo.findOneByFileGroupAndNameAndPath(fileGroup, fileName, fileDir).orElse(null);
	}

	public File findOneByFileGroupIdAndNameAndPath(Long fgId, String fileName, String filePath) {
		return fileRepo.findOneByFileGroupIdAndNameAndPath(fgId, fileName, filePath).orElse(null);
	}

	public List<VersionDto> getVersionsDto(Long id) {
		List<Version> versions = getVersions(id);
		if (versions == null) {
			return null;
		}
		return ModelMapperUtil.mapAll(versions, VersionDto.class);
	}

	public List<Version> getVersions(Long id) {
		return this.versionFileService.findAllByFileIdOrderByCreateTimeDesc(id);
	}

	public List<File> findAllByFileGroupId(Long id) {
		return this.fileRepo.findAllByFileGroupId(id);
	}

	public List<FileDto> findAllByFileGroupIdDto(Long id) {
		List<File> entitys = findAllByFileGroupId(id);
		if (entitys == null) {
			return null;
		}
		return ModelMapperUtil.mapAll(entitys, FileDto.class);
	}

	public FileDto updateFilePath(UploadFileRequest entity) {
		Long fgId = entity.getFgroupId();
		Long fId = entity.getId();
		String fileName = entity.getName();
		String filePath = entity.getDir();
		Util.checkFilePath(fileName, filePath);
		filePath = Path.of(filePath).toString();
		if (fgId == null) {
			throw new RuntimeException("file group id == null!");
		}
		if (fId == null) {
			throw new RuntimeException("file id == null!");
		}
		File file = this.findOneByFileGroupIdAndNameAndPath(fgId, fileName, filePath);
		if (file != null) {
			if (file.getId() != fId) {
				throw new RuntimeException("file path has exists!");
			} else {
				return ModelMapperUtil.map(file, FileDto.class);
			}
		} else {
			file = this.findOneByIdAndFileGroupId(fId, fgId);
		}
		if (file == null) {
			throw new RuntimeException(String.format("file not exists! file id: %s, file group id: %s", fId, fgId));
		}
		file.setName(fileName);
		file.setPath(filePath);
		return ModelMapperUtil.map(fileRepo.save(file), FileDto.class);
	}

	public FileVersionDto getFileVersion(Long id, String version) {
		FileDto fileDto = this.findOneDto(id);
		VersionDto versionDto;
		if (version == null) {
			versionDto = this.versionFileService.findDtoFirstByFileIdOrderByCreateTimeDesc(id);
		} else {
			versionDto = this.versionFileService.findDtoByFileIdAndName(id, version);
		}
		if (versionDto == null) {
			throw new RuntimeException(String.format("Version %s not found!", version));
		}
		FileVersionDto fileVersionDto = new FileVersionDto();
		fileVersionDto.setName(fileDto.getName());
		fileVersionDto.setPath(fileDto.getPath());
		fileVersionDto.setMd5(versionDto.getMd5());
		fileVersionDto.setLocalPath(versionDto.getPath());
		fileVersionDto.setDescription(versionDto.getDescription());
		return fileVersionDto;
	}
	
	public DownloadFileResponse downloadFile(Long id, String version) {
		DownloadFileResponse fileResponse = new DownloadFileResponse();
		FileVersionDto fileVersionDto = getFileVersion(id, version);
		ModelMapperUtil.update(fileVersionDto, fileResponse);
		Resource resource = storageService.loadFileAsResource(fileResponse.getLocalPath());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION
				, String.format( "attachment; %s", Util.objectToHearderElements(fileVersionDto)));
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
		headers.add("entity",Util.objectToString(fileVersionDto));
		fileResponse.setHeaders(headers);
		fileResponse.setResource(resource);
		return fileResponse;
	}

}
