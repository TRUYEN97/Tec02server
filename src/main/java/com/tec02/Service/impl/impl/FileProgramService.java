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
import com.tec02.model.dto.impl.VersionProgramDto;
import com.tec02.model.dto.impl.impl.impl.FileDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.FileProgramDto;
import com.tec02.model.dto.updownload.FileVersionDto;
import com.tec02.model.dto.updownload.UploadFileRequest;
import com.tec02.model.dto.updownload.impl.FileVersionPathDto;
import com.tec02.model.dto.updownload.impl.impl.DownloadFileResponse;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.VersionProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;
import com.tec02.repository.impl.VersionFileProgramRepo;
import com.tec02.repository.impl.fileProgramRepo;
import com.tec02.util.ModelMapperUtil;
import com.tec02.util.Util;

import jakarta.transaction.Transactional;

@Service
public class FileProgramService extends BaseService<FileProgramDto, FileProgram> {

	@Autowired
	private FileSystemStorageService storageService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private fileProgramRepo fileProgramRepo;

	@Autowired
	private VersionFileProgramRepo versionFileProgramRepo;

	protected FileProgramService(fileProgramRepo repository) {
		super(repository, FileProgramDto.class, FileProgram.class);
	}

	@Transactional
	public FileProgramDto upload(String pName, String sName, String lName, UploadFileRequest entity,
			MultipartFile multipartFile) {
		String version = entity.getVersion();
		String fileName = entity.getName();
		String filePath = entity.getDir();
		String des = entity.getDescription();
		Long fileId = entity.getId();
		Util.checkFilePath(fileName, filePath);
		filePath = filePath == null? "": Path.of(filePath).toString();
		if (multipartFile.isEmpty()) {
			throw new RuntimeException("Failed to store empty file " + multipartFile.getOriginalFilename());
		}
		if (Util.isInvalidVersion(version)) {
			throw new RuntimeException(String.format("version(%s) format not match x.x.x", version));
		}
		if (des == null || des.isBlank()) {
			throw new RuntimeException(String.format("invalid description"));
		}
		FileProgram file;
		if (fileId == null || (file = this.findOneById(fileId)) == null
				&& (file = this.findOneByNameAndPath(fileName, filePath)) == null) {
			Location location = this.locationService.createLocation(pName, sName, lName);
			if (location == null) {
				throw new RuntimeException(String.format("invalid location"));
			}
			file = new FileProgram();
			file.setName(fileName);
			file.setPath(filePath);
			file.setLocation(location);
			file.setDescription(des);
			file = update(file.getId(), file);
		}
		String dir = null;
		VersionProgram LastVersion;
		if ((LastVersion = this.versionFileProgramRepo.findFirstByFileProgramIdOrderByCreateTimeDesc(file.getId())
				.orElse(null)) != null) {
			String oldVersion = LastVersion.getName();
			if (Util.isInvalidVersion(oldVersion)) {
				throw new RuntimeException(String.format("Last version(%s) format not match x.x.x", oldVersion));
			}
			if (!Util.isNewVersionGreaterThanOldVersion(version, oldVersion)) {
				throw new RuntimeException(String.format("The version must greater than %s", oldVersion));
			}
			dir = StringUtils.cleanPath(String.format("%s/%s", Path.of(LastVersion.getPath()).getParent(), version));
		} else {
			dir = StringUtils.cleanPath(String.format("%s_program/%s/%s/%s", file.getId(), entity.getName(),
					System.currentTimeMillis(), version));
		}
		String localFilePath = storageService.storeProgram(multipartFile, dir);
		try {
			VersionProgram verEntity = new VersionProgram();
			verEntity.setName(version);
			verEntity.setPath(localFilePath);
			verEntity.setMd5(Util.md5File(localFilePath));
			verEntity.setDescription(Util.subInLength(entity.getDescription(), 255));
			verEntity.setFileProgram(file);
			verEntity.setEnable(entity.getEnable());
			versionFileProgramRepo.save(verEntity);
			return findOneDto(file.getId());
		} catch (Exception e) {
			Path path = Path.of(localFilePath);
			path.toFile().delete();
			path.getParent().toFile().delete();
			throw e;
		}

	}

	private FileProgram findOneById(Long fileId) {
		if (fileId == null) {
			return null;
		}
		return fileProgramRepo.findById(fileId).orElse(null);
	}

	public FileProgram findOneByNameAndPath(String fileName, String fileDir) {
		return fileProgramRepo.findOneByNameAndPath(fileName, fileDir).orElse(null);
	}

	public List<VersionProgramDto> getVersionsDto(Long id) {
		List<VersionProgram> versions = getVersions(id);
		if (versions == null) {
			return null;
		}
		return ModelMapperUtil.mapAll(versions, VersionProgramDto.class);
	}

	public List<VersionProgram> getVersions(Long id) {
		return this.versionFileProgramRepo.findAllByFileProgramIdOrderByCreateTimeDesc(id);
	}

	public FileDto updateFilePath(UploadFileRequest entity) {
		Long fId = entity.getId();
		String fileName = entity.getName();
		String filePath = entity.getDir();
		Boolean enable = entity.getEnable();
		Util.checkFilePath(fileName, filePath);
		filePath = Path.of(filePath).toString();
		if (fId == null) {
			throw new RuntimeException("file id == null!");
		}
		FileProgram file = this.findOneByNameAndPath(fileName, filePath);
		if (file != null) {
			long id = file.getId();
			if (id != fId) {
				throw new RuntimeException("file path has exists!");
			}
		} else {
			file = this.findOneById(fId);
		}
		if (file == null) {
			throw new RuntimeException(String.format("file not exists! file id: %s", fId));
		}
		List<VersionProgram> versions = file.getVersions();
		if (versions != null && !versions.isEmpty()) {
			VersionProgram version = versions.get(0);
			if (!version.isEnable() && enable != null && enable) {
				version.setEnable(true);
			}
		}
		file.setName(fileName);
		file.setPath(filePath);
		return ModelMapperUtil.map(fileProgramRepo.save(file), FileDto.class);
	}

	public FileVersionPathDto getFileVersion(Long id, String version) {
		FileProgramDto fileDto = this.findOneDto(id);
		VersionProgram versionDto;
		if (version == null) {
			versionDto = this.versionFileProgramRepo.findFirstByFileProgramIdOrderByCreateTimeDesc(id).orElse(null);
		} else {
			versionDto = this.versionFileProgramRepo.findOneByFileProgramIdAndName(id, version).orElse(null);
		}
		if (versionDto == null) {
			throw new RuntimeException(String.format("Version %s not found!", version));
		}
		FileVersionPathDto fileVersionDto = new FileVersionPathDto();
		fileVersionDto.setFilename(fileDto.getName());
		fileVersionDto.setFilepath(fileDto.getPath());
		fileVersionDto.setMd5(versionDto.getMd5());
		fileVersionDto.setLocalPath(versionDto.getPath());
		return fileVersionDto;
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
		FileProgram file = this.findOne(id);
		if (file == null) {
			return;
		}
		try {
			for (VersionProgram version : file.getVersions()) {
				deleteVersion(version);
			}
			this.fileProgramRepo.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException(String.format("delete file id = %s failed!", id), e);
		}
	}

	@Transactional
	private void deleteVersion(VersionProgram version) throws Exception {
		this.versionFileProgramRepo.deleteById(version.getId());
		this.storageService.deleteProgram(version.getPath());
	}

	public void deleteFiles(Long... ids) {
		for (Long id : ids) {
			deleteFile(id);
		}
	}

	public List<FileProgramDto> findAllByLocation(List<Location> locations, String name) {
		name = String.format("%%%s%%", name == null ? "" : name);
		return convertToDtos(this.fileProgramRepo.findAllByLocationIn(locations, name));
	}

}
