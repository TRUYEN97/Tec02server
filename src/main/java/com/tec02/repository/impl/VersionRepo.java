package com.tec02.repository.impl;

import java.util.List;

import com.tec02.model.entity.impl.Version;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.repository.IBaseRepo;

public interface VersionRepo extends IBaseRepo<Version> {

	Version findFirstByFileOrderByCreateTimeDesc(File fileOld);
	
	Version findFirstByFileIdOrderByCreateTimeDesc(Long id);

	List<Version> findAllByFileOrderByCreateTimeDesc(File file);
	
	List<Version> findAllByFileIdOrderByCreateTimeDesc(Long id);

}
