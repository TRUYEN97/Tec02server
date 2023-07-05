package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.impl.VersionDto;
import com.tec02.model.entity.impl.Version;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.repository.IBaseRepo;
import com.tec02.repository.impl.VersionRepo;

@Service
public class VersionFileService extends BaseService<VersionDto, Version> {

	@Autowired
	private VersionRepo versionRepo;
	
	protected VersionFileService(IBaseRepo<Version> repository) {
		super(repository, VersionDto.class, Version.class);
	}

	public Version findFirstByFileOrderByCreateTimeDesc(File file) {
		return this.versionRepo.findFirstByFileOrderByCreateTimeDesc(file);
	}

	public List<Version> findAllByFileIdOrderByCreateTimeDesc(Long id) {
		return this.versionRepo.findAllByFileIdOrderByCreateTimeDesc(id);
	}

	public Version findFirstByFileIdOrderByCreateTimeDesc(Long id) {
		return this.versionRepo.findFirstByFileIdOrderByCreateTimeDesc(id);
	}

}
