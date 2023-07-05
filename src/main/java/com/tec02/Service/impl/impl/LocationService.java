package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.LocationRequest;
import com.tec02.model.dto.impl.impl.LocationDto;
import com.tec02.model.entity.impl.Line;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.Product;
import com.tec02.model.entity.impl.Station;
import com.tec02.repository.impl.LocationRepo;

@Service
public class LocationService extends BaseService<LocationDto, Location> {

	protected LocationService(LocationRepo repository) {
		super(repository, LocationDto.class, Location.class);
	}

	@Autowired
	private ProductService productService;
	@Autowired
	private StationService stationService;
	@Autowired
	private LineService lineService;

	@Autowired
	private LocationRepo locationRepo;

	
	public List<Location> findAllLocation(LocationRequest loactionRequest) throws Exception {
		if (loactionRequest == null) {
			return null;
		}
		Long pID = loactionRequest.getProductId();
		Long sID = loactionRequest.getStationId();
		Long lID = loactionRequest.getLineId();
		if (pID != null) {
			if (sID != null && lID != null) {
				return this.locationRepo.findAllByProductIdAndStationIdAndLineId(pID, sID, lID);
			} else if (sID != null && lID == null) {
				return this.locationRepo.findAllByProductIdAndStationId(pID, sID);
			} else if (sID == null && lID != null){
				return this.locationRepo.findAllByProductIdAndLineId(pID, lID);
			}else {
				return this.locationRepo.findAllByProductId(pID);
			}
		} else if (sID != null) {
			if (lID != null) {
				return this.locationRepo.findAllByStationIdAndLineId(sID, lID);
			} else {
				return this.locationRepo.findAllByStationId(sID);
			}
		} else if (lID != null) {
			return this.locationRepo.findAllByLineId(lID);
		} else {
			return List.of();
		}
	}

	public boolean existsBy(LocationRequest loactionRequest) {
		if (loactionRequest == null) {
			return false;
		}
		Long pID = loactionRequest.getProductId();
		Long sID = loactionRequest.getStationId();
		Long lID = loactionRequest.getLineId();
		if (pID != null) {
			if (sID != null && lID != null) {
				return this.locationRepo.existsByProductIdAndStationIdAndLineId(pID, sID, lID);
			} else if (sID != null && lID == null) {
				return this.locationRepo.existsByProductIdAndStationId(pID, sID);
			} else if (sID == null && lID != null) {
				return this.locationRepo.existsByProductIdAndLineId(pID, lID);
			}else {
				return this.locationRepo.existsByProductId(pID);
			}
		} else if (sID != null) {
			if (lID != null) {
				return this.locationRepo.existsByStationIdAndLineId(sID, lID);
			} else {
				return this.locationRepo.existsByStationId(sID);
			}
		} else if (lID != null) {
			return this.locationRepo.existsByLineId(lID);
		} else {
			return false;
		}
	}

	public Location createLocation(LocationRequest loactionRequest) throws Exception {
		if (loactionRequest == null || (loactionRequest.getProductId() == null && loactionRequest.getStationId() == null
				&& loactionRequest.getLineId() == null)) {
			throw new Exception("Invalid location information!");
		}
		Location location = this.findLocation(loactionRequest);
		if (location != null) {
			return location;
		}
		Product product = null;
		if (loactionRequest.getProductId() != null) {
			product = this.productService.findOne(loactionRequest.getProductId());
			if (product == null) {
				throw new Exception("Invalid product id!");
			}
		}
		Station station = null;
		if (loactionRequest.getStationId() != null) {
			station = this.stationService.findOne(loactionRequest.getStationId());
			if (station == null) {
				throw new Exception("Invalid station id!");
			}
		}
		Line line = null;
		if (loactionRequest.getLineId() != null) {
			line = this.lineService.findOne(loactionRequest.getLineId());
			if (line == null) {
				throw new Exception("Invalid line id!");
			}
		}
		Location locationNew = new Location();
		locationNew.setProduct(product);
		locationNew.setStation(station);
		locationNew.setLine(line);
		locationNew.setName(String.format("%s_%s_%s", product, station, line));
		return this.update(null, locationNew);
	}

	public List<Location> findAllLocation(String pName, String sName, String lName) {
		if (pName != null) {
			if (sName != null && lName != null) {
				return this.locationRepo.findAllByProductNameAndStationNameAndLineName(pName, sName, lName);
			} else if (sName != null && lName == null) {
				return this.locationRepo.findAllByProductNameAndStationName(pName, sName);
			} else if (sName == null && lName != null) {
				return this.locationRepo.findAllByProductNameAndLineName(pName, lName);
			}else {
				return this.locationRepo.findAllByProductName(pName);
			}
		} else if (sName != null) {
			if (lName != null) {
				return this.locationRepo.findAllByStationNameAndLineName(sName, lName);
			} else {
				return this.locationRepo.findAllByStationName(sName);
			}
		} else if (lName != null) {
			return this.locationRepo.findAllByLineName(lName);
		} else {
			return List.of();
		}
	}

	public Location createLocation(String pName, String sName, String lName) throws Exception {
		if (pName == null && sName == null && lName == null) {
			throw new Exception("Invalid location information!");
		}
		Location location = this.findLocationName(pName, sName, lName);
		if (location != null) {
			return location;
		}
		Product product = null;
		if (pName != null) {
			product = this.productService.findOneByName(pName);
			if (product == null) {
				throw new Exception("Invalid product name!");
			}
		}
		Station station = null;
		if (sName != null) {
			station = this.stationService.findOneByName(sName);
			if (station == null) {
				throw new Exception("Invalid station name!");
			}
		}
		Line line = null;
		if (lName != null) {
			line = this.lineService.findOneByName(lName);
			if (line == null) {
				throw new Exception("Invalid line name!");
			}
		}
		Location locationNew = new Location();
		locationNew.setProduct(product);
		locationNew.setStation(station);
		locationNew.setLine(line);
		locationNew.setName(String.format("%s_%s_%s", product, station, line));
		return this.update(null, locationNew);
	}	public Location findLocation(LocationRequest loactionRequest){
		if (loactionRequest == null) {
			return null;
		}
		return createLocation(loactionRequest.getProductId(),
				loactionRequest.getStationId(), loactionRequest.getLineId());
	}
	
	public Location findLocationName(String pName, String sName, String lName){
		return this.locationRepo.findOneByProductNameAndStationNameAndLineName(pName,sName, lName).orElse(null);
	}

	public Location createLocation(Long pId, Long sId, Long lID) {
		return this.locationRepo.findOneByProductIdAndStationIdAndLineId(pId,sId, lID).orElse(null);
	}

}
