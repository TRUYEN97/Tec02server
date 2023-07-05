package com.tec02.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.Pc;
import com.tec02.repository.IBaseRepo;

public interface PcRepository extends IBaseRepo<Pc>{
	@Query("select e from Pc e where e.location in ?1 and e.name like ?2")
	List<Pc> findAllByLocationIn(List<Location> ids, String name);
}
