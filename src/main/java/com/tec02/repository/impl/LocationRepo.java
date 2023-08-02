package com.tec02.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tec02.model.entity.impl.Location;
import com.tec02.repository.IBaseRepo;

public interface LocationRepo extends IBaseRepo<Location> {

	Optional<Location> findOneByProductIdAndStationIdAndLineId(Long pID, Long sID, Long lID) ;

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Location e WHERE (:pID is null OR e.product.name = 'All' OR e.product.id = :pID)"
			+ " AND (:sID is null OR e.station.name = 'All' or e.station.id = :sID)"
			+ " AND (:lID is null OR e.line.name = 'All' or e.line.id =:lID)")
	boolean existsByElementId(Long pID, Long sID, Long lID);

	@Query("SELECT e FROM Location e WHERE (:pID is null OR e.product.name = 'All' OR e.product.id = :pID)"
			+ " AND (:sID is null OR e.station.name = 'All' or e.station.id = :sID)"
			+ " AND (:lID is null OR e.line.name = 'All' or e.line.id =:lID)")
	List<Location> findAllByProductIdAndStationIdAndLineId(Long pID, Long sID, Long lID);
	
	@Query("SELECT e FROM Location e WHERE (:pName = 'All' OR e.product.name = 'All' OR e.product.name = :pName)"
			+ " AND (:sName = 'All' OR e.station.name = 'All' or e.station.name = :sName)"
			+ " AND (:lName = 'All' OR e.line.name = 'All' or e.line.name =:lName)")
	List<Location> findAllContainLocationName(@Param("pName") String pName,
			@Param("sName") String sName,@Param("lName") String lName);
}
