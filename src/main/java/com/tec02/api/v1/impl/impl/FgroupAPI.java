package com.tec02.api.v1.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.FGroupService;
import com.tec02.api.v1.impl.BaseApiV1Location;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.FileGroupDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;

@RestController
@RequestMapping("/api/v1/fgroup")
public class FgroupAPI extends BaseApiV1Location<FileGroupDto, FileGroup> {

	@Autowired
	private FGroupService groupService;
	

	protected FgroupAPI(FGroupService service) {
		super(service);
		this.setAllLocationElemMustNotBeNull(false);
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
