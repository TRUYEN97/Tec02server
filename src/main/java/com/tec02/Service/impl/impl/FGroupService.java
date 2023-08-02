package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.impl.impl.impl.impl.impl.FileGroupDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;
import com.tec02.repository.impl.FileGroupRepository;
@RestController
@RequestMapping("/api/v1/fgroup")
public class FGroupService extends BaseService<FileGroupDto, FileGroup> {

	@Autowired
	private FileGroupRepository fileGroupRepository;

	@Autowired
	private FileService fileService;
	
	protected FGroupService(FileGroupRepository repository) {
		super(repository, FileGroupDto.class, FileGroup.class);
	}

	public List<FileGroupDto> findAllByLocation(List<Location> locations, String name) {
		name = String.format("%%%s%%", name == null ? "" : name);
		return convertToDtos(this.fileGroupRepository.findAllByLocationIn(locations, name));
	}

	public boolean existsByFileNameAndFilePath(String fileName, String filePath) {
		FileGroup fileGroup = new FileGroup();
		File file = new File();
		file.setName(fileName);
		file.setPath(filePath);
		fileGroup.setFile(file);
		return this.exists(fileGroup);
	}

	public List<FileGroupDto> findByProgramsId(Long id) {
		if(id == null) {
			throw new RuntimeException("program id == null!");
		}
		return convertToDtos(this.fileGroupRepository.findByProgramsId(id));
	}

	public void deleteFileGroups(Long[] ids) {
		for (Long id : ids) {
			this.delete(id);
		}
		
	}
	
	public void delete(Long id) {
		if(id == null || !this.fileGroupRepository.existsById(id)) {
			return;
		}
		this.fileService.deleteFiles(this.fileService.findAllIdByFileGroupId(id));
		FileGroup fileGroup = this.findOne(id);
		fileGroup.removeAllProgram();
		fileGroupRepository.save(fileGroup);
		fileGroupRepository.delete(fileGroup);
	}

}
