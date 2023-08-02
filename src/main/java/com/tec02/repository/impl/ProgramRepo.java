package com.tec02.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.Program;
import com.tec02.repository.IBaseRepo;

public interface ProgramRepo extends IBaseRepo<Program>{

	@Query("select e from Program e where e.location in ?1 and e.name like ?2")
	List<Program> findAllByLocationIn(List<Location> locations, String name);

}
