package com.tec02.repository.impl;

import java.util.List;
import java.util.Optional;

import com.tec02.model.entity.impl.Version;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.repository.IBaseRepo;

public interface VersionRepo extends IBaseRepo<Version> {

	Optional<Version> findFirstByFileOrderByCreateTimeDesc(File fileOld);
	
	Optional<Version> findFirstByFileIdOrderByCreateTimeDesc(Long id);

	List<Version> findAllByFileOrderByCreateTimeDesc(File file);
	
	List<Version> findAllByFileIdOrderByCreateTimeDesc(Long id);

	Optional<Version> findOneByFileIdAndName(Long id, String version);

}
