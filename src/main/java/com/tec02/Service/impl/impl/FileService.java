package com.tec02.Service.impl.impl;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.Service.FileSystemStorageService;
import com.tec02.Service.impl.BaseService;
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
		Long fgroundID = entity.getFgroundId();
		Long fileId = entity.getId();
		if (fgroundID == null) {
			throw new RuntimeException("Invalid fgroundId, value == null");
		}
		if (isInvalidVersion(version)) {
			throw new RuntimeException(String.format("version(%s) format not match x.x.x", version));
		}
		if (entity.getDescription() == null || entity.getDescription().isBlank()) {
			throw new RuntimeException(String.format("invalid description"));
		}
		FileGroup fileGroup = this.groupService.findOne(fgroundID);
		if (fileGroup == null) {
			throw new RuntimeException(String.format("Not found file-group by id = %s", fgroundID));
		}
		File file;
		if ((file = this.findOneByIdAndFileGroupId(fileId, fgroundID)) == null 
				&& (file = this.findOneByFileGroupAndNameAndPath(fileGroup, entity.getName(), entity.getDir())) == null) {
			file = new File();
			file.setName(entity.getName());
			file.setPath(entity.getDir());
			file.setFileGroup(fileGroup);
			file = update(file.getId(), file);
		}
		String dir = null;
		Version LastVersion;
		if ((LastVersion = this.versionFileService.findFirstByFileIdOrderByCreateTimeDesc(file.getId())) != null) {
			String oldVersion = LastVersion.getName();
			if (isInvalidVersion(oldVersion)) {
				throw new RuntimeException(String.format("Last version(%s) format not match x.x.x", oldVersion));
			}
			if (!isNewVersionGreaterThanOldVersion(version, oldVersion)) {
				throw new RuntimeException(String.format("The version must greater than %s", oldVersion));
			}
			dir = StringUtils
					.cleanPath(String.format("%s/%s", Path.of(LastVersion.getPath()).getParent(), version));
		}else {
			dir = StringUtils.cleanPath(String.format("%s_%s/%s/%s/%s", fileGroup.getId(), fileGroup.getName(),
					entity.getName(), System.currentTimeMillis(), version));
		}
		String filePath = storageService.storeFile(multipartFile, dir);
		try {
			Version verEntity = new Version();
			verEntity.setName(version);
			verEntity.setPath(filePath);
			verEntity.setMd5(Util.md5File(filePath));
			verEntity.setDescription(Util.subInLength(entity.getDescription(), 255));
			verEntity.setFile(file);
			versionFileService.updateDto(verEntity.getId(), verEntity);
			return findOneDto(file.getId());
		} catch (Exception e) {
			Path path = Path.of(filePath);
			path.toFile().delete();
			path.getParent().toFile().delete();
			throw e;
		}

	}

	private File findOneByIdAndFileGroupId(Long fileId, Long fgroundID) {
		if(fileId == null || fgroundID == null) {
			return null;
		}
		return fileRepo.findOneByIdAndFileGroupId(fileId, fgroundID).orElse(null);
	}

	private boolean isNewVersionGreaterThanOldVersion(String version, String oldVersion) {
		if (version.equals(oldVersion) || isInvalidVersion(version) || isInvalidVersion(oldVersion)) {
			return false;
		}
		String[] vOldElems = oldVersion.split("\\.");
		String[] vNewElems = version.split("\\.");
		for (int i = 0; i < 3; i++) {
			int nV = Integer.valueOf(vNewElems[i]);
			int oV = Integer.valueOf(vOldElems[i]);
			if (nV > oV) {
				return true;
			}
		}
		return false;
	}

	private boolean isInvalidVersion(String version) {
		return version == null || !version.matches("^[0-9].[0-9].[0-9]$");
	}

	public File findOneByFileGroupAndNameAndPath(FileGroup fileGroup, String fileName, String fileDir) {
		return fileRepo.findOneByFileGroupAndNameAndPath(fileGroup, fileName, fileDir).orElse(null);
	}

	public List<VersionDto> getVersionsDto(Long id) {
		List<Version> versions = getVersions(id);
		if(versions == null) {
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
		if(entitys == null) {
			return null;
		}
		return ModelMapperUtil.mapAll(entitys, FileDto.class);
	}

}
