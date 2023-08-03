package com.tec02.api.v1.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tec02.Service.impl.BaseService;
import com.tec02.Service.impl.impl.LocationService;
import com.tec02.model.dto.IHaveLocationDto;
import com.tec02.model.dto.LocationRequest;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.entity.IHaveLocation;
import com.tec02.model.entity.impl.Location;

import lombok.Setter;

@Setter
public abstract class BaseApiV1Location<D extends IHaveLocationDto, E extends IHaveLocation> {

	@Autowired
	protected LocationService locationService;

	private boolean isAllLocationElemMustNotBeNull;

	@Autowired
	private final BaseService<D, E> service;

	protected BaseApiV1Location(BaseService<D, E> service) {
		this.service = service;
	}

	private boolean isInvalidLocation(Object pName, Object sName, Object lName) {
		if (isAllLocationElemMustNotBeNull) {
			return (pName == null || sName == null || lName == null);
		} else {
			return false;
		}
	}

	@PostMapping("/location")
	public ResponseEntity<ResponseDto> craeteWithLocation(@RequestParam(value = "pID", required = false) Long pID,
			@RequestParam(value = "sID", required = false) Long sID,
			@RequestParam(value = "lID", required = false) Long lID, @RequestBody E entity) {
		try {
			String name = entity.getName();
			if (name == null || name.isBlank()) {
				return ResponseDto.toResponse(false, null, "Name must not be null or empty!");
			}
			if (this.service.existsByName(name)) {
				return ResponseDto.toResponse(false, null, "Name(%s) has exists!", name);
			}
			if (isInvalidLocation(pID, sID, lID)) {
				return ResponseDto.toResponse(false, null, "Invalid location!");
			}
			Location location = this.locationService.createLocation(new LocationRequest(pID, sID, lID, null, null));
			entity.setLocation(location);
			D savedDto = this.service.updateDto(entity.getId(), entity);
			return ResponseDto.toResponse(true, savedDto, "save success!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PostMapping("/location/name")
	public ResponseEntity<ResponseDto> craeteWithLocationName(
			@RequestParam(value = "pName", required = false) String pName,
			@RequestParam(value = "sName", required = false) String sName,
			@RequestParam(value = "lName", required = false) String lName, @RequestBody E entity) {
		try {
			String name = entity.getName();
			if (name == null || name.isBlank()) {
				return ResponseDto.toResponse(false, null, "Name must not be null or empty!");
			}
			if (this.service.existsByName(name)) {
				return ResponseDto.toResponse(false, null, "Name(%s) has exists!", name);
			}
			if (isInvalidLocation(pName, sName, lName)) {
				return ResponseDto.toResponse(false, null, "Invalid location!");
			}
			Location location = this.locationService.createLocation(pName, sName, lName);
			entity.setLocation(location);
			D savedDto = this.service.updateDto(entity.getId(), entity);
			return ResponseDto.toResponse(true, savedDto, "save success!");
		} catch (Exception e) {
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping("/location/name")
	public ResponseEntity<ResponseDto> updateWithLocationName(@RequestParam(value = "lName") String lName,
			@RequestBody RequestDto dto) {
		try {
			if (lName == null) {
				return ResponseDto.toResponse(false, null, "Invaild line location!");
			}
			List<Long> targetId = dto.getIds();
			if (targetId == null || targetId.isEmpty()) {
				return ResponseDto.toResponse(false, null, "targetId == null or empty");
			}
			List<E> targets = this.service.findAllByIds(targetId);
			if (targets == null || targets.isEmpty()) {
				return ResponseDto.toResponse(false, null, "Nothing to update");
			}
			Location locationbase;
			Location location;
			List<D> targetDtos = new ArrayList<>();
			for (E entity : targets) {
				locationbase = entity.getLocation();
				if (locationbase == null || isInvalidLocation(locationbase.getProduct(), locationbase.getStation(),
						locationbase.getLine())) {
					this.service.delete(entity.getId());
					continue;
				}
				location = this.locationService.createLocation(locationbase.getProduct().getName(),
						locationbase.getStation().getName(), lName);
				if (location == null) {
					continue;
				}
				entity.setLocation(location);
				targetDtos.add(service.updateDto(entity.getId(), entity));
			}
			return ResponseDto.toResponse(true, targetDtos, "update ok!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/location")
	public ResponseEntity<ResponseDto> findWithLocation(@RequestParam(value = "pID", required = false) Long pID,
			@RequestParam(value = "sID", required = false) Long sID,
			@RequestParam(value = "lID", required = false) Long lID,
			@RequestParam(value = "name", required = false) String name) {
		try {
			List<D> dtos;
			if (pID == null && sID == null && lID == null) {
				if (name == null) {
					dtos = this.service.findAllDto();
				} else {
					dtos = this.service.findAllByNameLikeDto(name);
				}
			} else {
				List<Location> locations = this.locationService
							.findAllLocationEquals(new LocationRequest(pID, sID, lID, null, null));
				dtos = findAllByLocation(locations, name);
			}
			return ResponseDto.toResponse(true, dtos, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/location/name")
	public ResponseEntity<ResponseDto> findWithLocationName(
			@RequestParam(value = "pName", required = false) String pName,
			@RequestParam(value = "sName", required = false) String sName,
			@RequestParam(value = "lName", required = false) String lName,
			@RequestParam(value = "name", required = false) String name) {
		try {
			List<D> dtos;
			if (pName == null && sName == null && lName == null) {
				if (name == null) {
					dtos = this.service.findAllDto();
				} else {
					dtos = this.service.findAllByNameLikeDto(name);
				}
			} else {
				List<Location> locations = this.locationService.findAllLocationEquals(pName, sName, lName);
				dtos = findAllByLocation(locations, name);
			}
			return ResponseDto.toResponse(true, dtos, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	protected abstract List<D> findAllByLocation(List<Location> locations, String name);

}
