package com.tec02.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.LocationService;
import com.tec02.model.dto.LocationRequest;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.impl.LocationDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.util.ModelMapperUtil;

@RestController
@RequestMapping("api/location")
public class LocationAPI {

	@Autowired
	private LocationService locationSercive;

	@PostMapping()
	public ResponseEntity<ResponseDto> createLocation(@RequestBody LocationRequest locationRequest) {
		try {
			Location location = this.locationSercive.createLocation(locationRequest);
			return ResponseDto.toResponse(true, ModelMapperUtil.map(location, LocationDto.class), "create location ok");
		} catch (Exception e) {
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<ResponseDto> getLocation(@RequestParam(value = "pID", required = false) Long pID,
			@RequestParam(value = "sID", required = false) Long sID,
			@RequestParam(value = "lID", required = false) Long lID) {
		try {
			List<Location> locations;
			if (pID == null && sID == null && lID == null) {
				locations = this.locationSercive.findAll();
			} else {
				locations = this.locationSercive.findAllLocationEquals(new LocationRequest(pID, sID, lID, null, null));
			}
			return ResponseDto.toResponse(true, ModelMapperUtil.mapAll(locations, LocationDto.class), "ok");
		} catch (Exception e) {
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

}
