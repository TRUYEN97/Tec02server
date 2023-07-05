package com.tec02.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;
import com.tec02.repository.IBaseRepo;

public interface FileGroupRepository extends IBaseRepo<FileGroup> {
	@Query("select e from FileGroup e where e.location in ?1 and e.name like ?2")
	List<FileGroup> findAllByLocationIn(List<Location> ids, String name);
}
