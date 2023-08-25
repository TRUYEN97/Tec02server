package com.tec02.api.v1.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.FGroupService;
import com.tec02.Service.impl.impl.FileProgramService;
import com.tec02.Service.impl.impl.PcService;
import com.tec02.Service.impl.impl.ProgramService;
import com.tec02.api.v1.impl.BaseApiV1Location;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.VersionProgramDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.ProgramDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.Program;

@RestController
@RequestMapping("api/v1/program")
public class ProgramApi extends BaseApiV1Location<ProgramDto, Program> {

	protected ProgramApi(ProgramService service) {
		super(service);
		setAllLocationElemMustNotBeNull(false);
	}

	@Autowired
	private PcService pcService;

	@Autowired
	private FileProgramService fileProgramService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private FGroupService groupService;

	@GetMapping("/pcs")
	public ResponseEntity<ResponseDto> getPcs(@RequestParam(value = "id") Long id) {
		try {
			ProgramDto programDto = this.programService.findOneDto(id);
			List<Location> locations = this.locationService.findAllLocationEquals(programDto.getProduct(),
					programDto.getStation(), programDto.getLine());
			return ResponseDto.toResponse(true, this.pcService.findAllByLocation(locations, null), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping("/fileprogram")
	public ResponseEntity<ResponseDto> updateProgramFileprogram(
			@RequestParam(value = "action", required = false) Boolean remove,
			@RequestParam(value = "id", required = false) Long id, @RequestBody RequestDto requestDto) {
		try {
			if (remove == null || !remove) {
				return ResponseDto.toResponse(true, this.programService.addFileProgram(id, requestDto.getId()), "ok");
			} else {
				return ResponseDto.toResponse(true, this.programService.removeFileProgram(id), "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/fileprograms")
	public ResponseEntity<ResponseDto> findAllFileProgram(@RequestParam(value = "id") Long id) {
		try {
			ProgramDto programDto = this.programService.findOneDto(id);
			List<Location> locations = this.locationService.findAllLocationEquals(programDto.getProduct(),
					programDto.getStation(), programDto.getLine());
			return ResponseDto.toResponse(true, this.fileProgramService.findAllByLocation(locations, null), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/fileprogram/version")
	public ResponseEntity<ResponseDto> findAppFileVersions(@RequestParam("id") Long id) {
		try {
			Program program = this.programService.findOne(id);
			FileProgram fileProgram = program.getFileProgram();
			if (fileProgram == null) {
				return ResponseDto.toResponse(true, null, "program (%s) not have program file", id);
			}
			List<VersionProgramDto> dtos = fileProgramService.getVersionsDto(fileProgram.getId());
			return ResponseDto.toResponse(true, dtos, "Version of id= %s", id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

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

	@GetMapping("/fgroup")
	public ResponseEntity<ResponseDto> findWithProgramId(@RequestParam(value = "id") Long id) {
		try {
			return ResponseDto.toResponse(true, this.groupService.findByProgramsId(id), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/fgroups")
	public ResponseEntity<ResponseDto> findAllWithProgramLocation(@RequestParam(value = "id") Long id) {
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

	@PutMapping()
	public ResponseEntity<ResponseDto> updateProgram(@RequestParam(value = "id") Long id, @RequestBody ProgramDto dto) {
		try {
			return ResponseDto.toResponse(true, this.programService.update(id, dto), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseDto> delete(@RequestParam(value = "id", required = false) Long... ids) {
		try {
			this.programService.deletePrograms(ids);
			return ResponseDto.toResponse(true, ids, "ok");
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
