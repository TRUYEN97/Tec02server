package com.tec02.api.v1.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.StationService;
import com.tec02.api.v1.BaseApiV1;
import com.tec02.model.dto.impl.StationDto;
import com.tec02.model.entity.impl.Station;

@RestController
@RequestMapping("api/v1/station")
public class StationAPI extends BaseApiV1<StationDto, Station> {
	
	protected StationAPI(StationService service) {
		super(service);
	}

}
