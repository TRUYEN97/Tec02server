package com.tec02.api.v1.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.PcService;
import com.tec02.Service.impl.impl.ProgramService;
import com.tec02.api.v1.impl.BaseApiV1Location;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.impl.impl.impl.PcDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.ProgramDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.Pc;

@RestController
@RequestMapping("/api/v1/pc")
public class PcAPI extends BaseApiV1Location<PcDto, Pc> {

	@Autowired
	private PcService pcService;
	
	@Autowired
	private ProgramService programService;
	
	protected PcAPI(PcService service) {
		super(service);
	}
	
	@GetMapping("/program")
	public ResponseEntity<ResponseDto> findWithProgramId(
			@RequestParam(value = "id") Long id) {
		try {
			ProgramDto programDto = this.programService.findOneDto(id);
			List<Location> locations = this.locationService.findAllLocationEquals(
					programDto.getProduct()
					, programDto.getStation()
					, programDto.getLine());
			return ResponseDto.toResponse(true, this.pcService.findAllByLocation(locations, null), "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping("/app/info")
	public ResponseEntity<ResponseDto> getAppInfo(@RequestParam(value = "pcname")  String pcName) {
		try {
			if(pcName == null) {
				return ResponseDto.toResponse(false, null, "pcname == null!");
			}else {
				PcDto pc = this.pcService.findOneDtoByName(pcName);
				if(pc == null) {
					return ResponseDto.toResponse(false, null, "Pc name not existe!");
				}
				List<Location> locations = this.locationService.findAllLocationEquals(
						pc.getProduct()
						, pc.getStation()
						, pc.getLine());
				return ResponseDto.toResponse(true,
						this.programService.getAllProgramsFileByLocations(locations), "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@Override
	protected List<PcDto> findAllByLocation(List<Location> locations, String name) {
		return this.pcService.findAllByLocation(locations, name);
	}

}
