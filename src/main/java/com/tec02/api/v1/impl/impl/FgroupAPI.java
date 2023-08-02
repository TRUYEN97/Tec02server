package com.tec02.api.v1.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.FGroupService;
import com.tec02.Service.impl.impl.ProgramService;
import com.tec02.api.v1.impl.BaseApiV1Location;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.FileGroupDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.ProgramDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;

@RestController
@RequestMapping("/api/v1/fgroup")
public class FgroupAPI extends BaseApiV1Location<FileGroupDto, FileGroup> {

	@Autowired
	private FGroupService groupService;
	

	@Autowired
	private ProgramService programService;

	protected FgroupAPI(FGroupService service) {
		super(service);
		this.setAllLocationElemMustNotBeNull(false);
	}

	@GetMapping("/program")
	public ResponseEntity<ResponseDto> findWithProgramId(@RequestParam(value = "id") Long id) {
		try {
			return ResponseDto.toResponse(true, this.groupService.findByProgramsId(id), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/program/location")
	public ResponseEntity<ResponseDto> findAllWithProgramLocation(
			@RequestParam(value = "id", required = false) Long id) {
		try {
			ProgramDto programDto = this.programService.findOneDto(id);
			List<Location> locations = this.locationService.findAllLocationEquals(programDto.getProduct(),
					programDto.getStation(), programDto.getLine());
			return ResponseDto.toResponse(true, this.groupService.findAllByLocation(locations, null), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<ResponseDto> delete(@RequestParam(value = "id", required = false) Long... ids) {
		try {
			this.groupService.deleteFileGroups(ids);
			return ResponseDto.toResponse(true, ids, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@Override
	protected List<FileGroupDto> findAllByLocation(List<Location> locations, String name) {
		return this.groupService.findAllByLocation(locations, name);
	}

}
