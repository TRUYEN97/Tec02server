package com.tec02.repository.impl;

import java.util.List;
import java.util.Optional;

import com.tec02.model.entity.impl.VersionProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;
import com.tec02.repository.IBaseRepo;

public interface VersionFileProgramRepo extends IBaseRepo<VersionProgram> {

	Optional<VersionProgram> findFirstByFileProgramOrderByCreateTimeDesc(FileProgram fileProgram);
	
	Optional<VersionProgram> findFirstByFileProgramIdOrderByCreateTimeDesc(Long id);

	List<VersionProgram> findAllByFileProgramOrderByCreateTimeDesc(FileProgram file);
	
	List<VersionProgram> findAllByFileProgramIdOrderByCreateTimeDesc(Long id);

	Optional<VersionProgram> findOneByFileProgramIdAndName(Long id, String version);

}
