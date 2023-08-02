package com.tec02.api.v1.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.ProgramService;
import com.tec02.api.v1.impl.BaseApiV1Location;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.ProgramDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.Program;

@RestController
@RequestMapping("api/v1/program")
public class ProgramApi extends BaseApiV1Location<ProgramDto, Program> {

	protected ProgramApi(ProgramService service) {
		super(service);
		setAllLocationElemMustNotBeNull(false);
	}

	@Autowired
	private ProgramService programService;

	@PutMapping("/fgroup")
	public ResponseEntity<ResponseDto> updateProgramFgroup(
			@RequestParam(value = "action", required = false) Boolean remove,
			@RequestParam(value = "id", required = false) Long id, @RequestBody RequestDto requestDto) {
		try {
			if (remove == null || !remove) {
				return ResponseDto.toResponse(true, this.programService.addFileGroup(id, requestDto), "ok");
			} else {
				return ResponseDto.toResponse(true, this.programService.removeFileGroup(id, requestDto), "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping("/fileprogram")
	public ResponseEntity<ResponseDto> updateProgramFileprogram(@RequestParam(value = "id", required = false) Long id,
			@RequestBody RequestDto requestDto) {
		try {
			return ResponseDto.toResponse(true, this.programService.addFileProgram(id, requestDto.getId()), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping()
	public ResponseEntity<ResponseDto> updateProgram(@RequestParam(value = "id") Long id, @RequestBody ProgramDto dto) {
		try {
			return ResponseDto.toResponse(true, this.programService.update(id, dto), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@Override
	protected List<ProgramDto> findAllByLocation(List<Location> locations, String name) {
		return this.programService.findAllByLocation(locations, name);
	}
}
