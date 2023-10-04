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
import com.tec02.model.dto.impl.VersionDto;
import com.tec02.model.dto.impl.impl.impl.FileDto;
import com.tec02.model.dto.updownload.FileVersionDto;
import com.tec02.model.dto.updownload.UploadFileRequest;
import com.tec02.model.dto.updownload.impl.FileVersionPathDto;
import com.tec02.model.dto.updownload.impl.impl.DownloadFileResponse;
import com.tec02.model.entity.impl.Version;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;
import com.tec02.repository.impl.FileGroupRepository;
import com.tec02.repository.impl.fileRepo;
import com.tec02.util.ModelMapperUtil;
import com.tec02.util.Util;

import jakarta.transaction.Transactional;

@Service
public class FileService extends BaseService<FileDto, File> {

	@Autowired
	private FileSystemStorageService storageService;

	@Autowired
	private FileGroupRepository fileGroupRepository;

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
		Long fgroupID = entity.getParentId();
		Long fileId = entity.getId();
		Util.checkFilePath(fileName, filePath);
		filePath = filePath == null ? "" : Path.of(filePath).toString();
		if (fgroupID == null) {
			throw new RuntimeException("Invalid fgroupID, value == null");
		}
		if (Util.isInvalidVersion(version)) {
			throw new RuntimeException(String.format("version(%s) format not match x.x.x", version));
		}
		if (entity.getDescription() == null || entity.getDescription().isBlank()) {
			throw new RuntimeException(String.format("invalid description"));
		}
		FileGroup fileGroup;
		if (fgroupID == null || (fileGroup = this.fileGroupRepository.findById(fgroupID).orElse(null)) == null) {
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
			verEntity.setEnable(entity.getEnable());
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
		Long fgId = entity.getParentId();
		Long fId = entity.getId();
		String fileName = entity.getName();
		String filePath = entity.getDir();
		Boolean enable = entity.getEnable();
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
			long id = file.getId();
			if (fId != id) {
				throw new RuntimeException(String.format("file path has exists! %s-%s", id, fId));
			}
		} else {
			file = this.findOneByIdAndFileGroupId(fId, fgId);
		}
		if (file == null) {
			throw new RuntimeException(String.format("file not exists! file id: %s, file group id: %s", fId, fgId));
		}
		List<Version> versions = file.getVersions();
		if (versions != null && !versions.isEmpty()) {
			Version version = versions.get(0);
			if (!version.isEnable() && enable != null && enable) {
				version.setEnable(true);
			}
		}
		file.setName(fileName);
		file.setPath(filePath);
		return ModelMapperUtil.map(fileRepo.save(file), FileDto.class);
	}

	public FileVersionPathDto getFileVersion(Long id, String versionCode) {
		File fileDto = this.findOne(id);
		FileVersionPathDto fileVersionDto = new FileVersionPathDto();
		fileVersionDto.setFilename(fileDto.getName());
		fileVersionDto.setFilepath(fileDto.getPath());
		String verName;
		for (Version version : fileDto.getVersions()) {
			verName = version.getName();
			if ((versionCode == null && version.isEnable()) || (verName != null && verName.equals(versionCode))) {
				fileVersionDto.setMd5(version.getMd5());
				fileVersionDto.setLocalPath(version.getPath());
				return fileVersionDto;
			}
		}
		throw new RuntimeException(String.format("Version %s not found!", versionCode));
	}

	public DownloadFileResponse downloadFile(Long id, String version) {
		DownloadFileResponse fileResponse = new DownloadFileResponse();
		FileVersionDto fileVersionDto = getFileVersion(id, version);
		ModelMapperUtil.update(fileVersionDto, fileResponse);
		Resource resource = storageService.loadFileAsResource(fileResponse.getLocalPath());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION,
				String.format("attachment; %s", Util.objectToHearderElements(fileVersionDto)));
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
		fileResponse.setHeaders(headers);
		fileResponse.setResource(resource);
		return fileResponse;
	}

	public void deleteFile(Long id) {
		File file = this.findOne(id);
		if (file == null) {
			return;
		}
		try {
			for (Version version : file.getVersions()) {
				deleteVersion(version);
			}
			this.fileRepo.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException(String.format("delete file id = %s failed!", id), e);
		}
	}

	@Transactional
	private void deleteVersion(Version version) throws Exception {
		this.versionFileService.delete(version.getId());
		this.storageService.deleteFile(version.getPath());
	}

	public List<Long> findAllIdByFileGroupId(Long id) {
		if (id == null) {
			throw new RuntimeException("program id == null!");
		}
		return this.fileRepo.findAllIdByFileGroupId(id);
	}

	public void deleteFiles(List<Long> ids) {
		for (Long id : ids) {
			deleteFile(id);
		}
	}

}
