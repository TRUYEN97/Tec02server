package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.Service.impl.impl.impl.LineService;
import com.tec02.Service.impl.impl.impl.ProductService;
import com.tec02.Service.impl.impl.impl.StationService;
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

	public List<Location> findAllLocationEquals(LocationRequest locationRequest) throws Exception {
		if (locationRequest == null) {
			return null;
		}
		Long pID = locationRequest.getProductId();
		Long sID = locationRequest.getStationId();
		Long lID = locationRequest.getLineId();
		return this.locationRepo.findAllByProductIdAndStationIdAndLineId(pID, sID, lID);
	}

	public List<Location> findAllLocationEquals(String pName, String sName, String lName) {
		return this.locationRepo.findAllContainLocationName(checkName(pName), checkName(sName), checkName(lName));
	}

	public Location createLocation(LocationRequest loactionRequest) throws Exception {
		if (loactionRequest == null) {
			throw new Exception("Invalid location information!");
		}
		Long pID = loactionRequest.getProductId();
		Long sID = loactionRequest.getStationId();
		Long lID = loactionRequest.getLineId();
		Location location = this.locationRepo.findOneByProductIdAndStationIdAndLineId(pID, sID, lID).orElse(null);
		if (location != null) {
			return location;
		}
		Product product = this.productService.findByIdOrGetDefault(pID);
		Station station = this.stationService.findByIdOrGetDefault(sID);
		Line line = this.lineService.findByIdOrGetDefault(lID);
		Location locationNew = new Location();
		locationNew.setProduct(product);
		locationNew.setStation(station);
		locationNew.setLine(line);
		locationNew.setName(String.format("%s_%s_%s", product, station, line));
		return locationRepo.save(locationNew);
	}

	public Location createLocation(String pName, String sName, String lName) {
		Location location = this.findOneByName(pName, sName, lName);
		if (location != null) {
			return location;
		}
		Product product = this.productService.findByNameOrGetDefault(pName);
		Station station = this.stationService.findByNameOrGetDefault(sName);
		Line line = this.lineService.findByNameOrGetDefault(lName);
		Location locationNew = new Location();
		locationNew.setProduct(product);
		locationNew.setStation(station);
		locationNew.setLine(line);
		locationNew.setName(String.format("%s_%s_%s", product, station, line));
		return locationRepo.save(locationNew);
	}

	private Location findOneByName(String pName, String sName, String lName) {
		return this.locationRepo
				.findOneByName(String.format("%s_%s_%s", checkName(pName), checkName(sName), checkName(lName)))
				.orElse(null);
	}

	private String checkName(String name) {
		return name == null ? "All" : name;
	}

}
