package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.impl.VersionProgramDto;
import com.tec02.model.entity.impl.VersionProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;
import com.tec02.repository.IBaseRepo;
import com.tec02.repository.impl.VersionFileProgramRepo;

@Service
public class VersionProgramService extends BaseService<VersionProgramDto, VersionProgram> {

	@Autowired
	private VersionFileProgramRepo fileProgramRepo;
	
	protected VersionProgramService(IBaseRepo<VersionProgram> repository) {
		super(repository, VersionProgramDto.class, VersionProgram.class);
	}

	public VersionProgram findFirstByFileOrderByCreateTimeDesc(FileProgram file) {
		return this.fileProgramRepo.findFirstByFileProgramOrderByCreateTimeDesc(file).orElse(null);
	}

	public List<VersionProgram> findAllByFileIdOrderByCreateTimeDesc(Long id) {
		return this.fileProgramRepo.findAllByFileProgramIdOrderByCreateTimeDesc(id);
	}

	public VersionProgram findFirstByFileIdOrderByCreateTimeDesc(Long id) {
		return this.fileProgramRepo.findFirstByFileProgramIdOrderByCreateTimeDesc(id).orElse(null);
	}
	
	public VersionProgramDto findDtoFirstByFileIdOrderByCreateTimeDesc(Long id) {
		return convertToDto(this.fileProgramRepo.findFirstByFileProgramIdOrderByCreateTimeDesc(id).orElse(null));
	}

	public VersionProgramDto findDtoByFileIdAndName(Long id, String version) {
		return convertToDto(findByFileIdAndName(id, version));
	}
	
	public VersionProgram findByFileIdAndName(Long id, String version) {
		return this.fileProgramRepo.findOneByFileProgramIdAndName(id, version).orElse(null);
	}

}
