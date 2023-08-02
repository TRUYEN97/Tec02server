package com.tec02.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;
import com.tec02.repository.IBaseRepo;

public interface fileProgramRepo extends IBaseRepo<FileProgram> {

	Optional<FileProgram> findOneByNameAndPath(String fileName, String fileDir);
	
	@Query("select e from FileProgram e where e.location in ?1 and e.name like ?2")
	List<FileProgram> findAllByLocationIn(List<Location> locations, String name);
	
}
