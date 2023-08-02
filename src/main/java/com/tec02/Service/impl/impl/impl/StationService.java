package com.tec02.Service.impl.impl.impl;

import org.springframework.stereotype.Service;

import com.tec02.Service.impl.impl.AbsSaveDefaultLocationService;
import com.tec02.model.dto.impl.StationDto;
import com.tec02.model.entity.impl.Station;
import com.tec02.repository.impl.StationRepository;

@Service
public class StationService extends AbsSaveDefaultLocationService<StationDto, Station> {
	
	protected StationService(StationRepository repository) {
		super(repository, StationDto.class, Station.class);
	}
}
