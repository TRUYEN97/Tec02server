package com.tec02.api.v1.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.PcService;
import com.tec02.api.v1.impl.BaseApiV1Location;
import com.tec02.model.dto.impl.impl.impl.impl.PcDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.Pc;

@RestController
@RequestMapping("/api/v1/pc")
public class PcAPI extends BaseApiV1Location<PcDto, Pc> {

	@Autowired
	private PcService pcService;
	
	protected PcAPI(PcService service) {
		super(service);
	}

	@Override
	protected List<PcDto> findAllByLocation(List<Location> locations, String name) {
		return this.pcService.findAllByLocation(locations, name);
	}

}
