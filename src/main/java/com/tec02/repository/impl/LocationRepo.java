package com.tec02.repository.impl;

import java.util.List;
import java.util.Optional;

import com.tec02.model.entity.impl.Location;
import com.tec02.repository.IBaseRepo;

public interface LocationRepo extends IBaseRepo<Location> {

	Optional<Location> findOneByProductIdAndStationIdAndLineId(Long pID, Long sID, Long lID) ;

	boolean existsByProductIdAndStationIdAndLineId(Long productId, Long stationId, Long lineId);

	boolean existsByProductId(Long productId);

	boolean existsByProductIdAndStationId(Long productId, Long stationId);

	boolean existsByStationId(Long stationId);

	boolean existsByLineId(Long lineId);

	boolean existsByProductIdAndLineId(Long pID, Long lID);

	boolean existsByStationIdAndLineId(Long sID, Long lID);

	List<Location> findAllByProductIdAndStationIdAndLineId(Long productId, Long stationId, Long lineId);

	List<Location> findAllByProductIdAndStationId(Long pID, Long sID);

	List<Location> findAllByProductIdAndLineId(Long pID, Long sID);

	List<Location> findAllByStationIdAndLineId(Long sID, Long lID);

	List<Location> findAllByStationId(Long sID);

	List<Location> findAllByLineId(Long lID);

	List<Location> findAllByProductNameAndStationNameAndLineName(String pName, String sName, String lName);

	List<Location> findAllByProductNameAndStationName(String pName, String sName);

	List<Location> findAllByProductNameAndLineName(String pName, String sName);

	List<Location> findAllByStationNameAndLineName(String sName, String lName);

	List<Location> findAllByStationName(String sName);

	List<Location> findAllByLineName(String lName);

	Optional<Location> findOneByProductNameAndStationNameAndLineName(String pName, String sName, String lName);

	List<Location> findAllByProductId(Long pID);

	List<Location> findAllByProductName(String pName);
}
