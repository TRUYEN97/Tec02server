package com.tec02.Service.impl.impl.impl;

import org.springframework.stereotype.Service;

import com.tec02.Service.impl.impl.AbsSaveDefaultLocationService;
import com.tec02.model.dto.impl.LineDto;
import com.tec02.model.entity.impl.Line;
import com.tec02.repository.impl.LineRepository;

@Service
public class LineService extends AbsSaveDefaultLocationService<LineDto, Line> {

	protected LineService(LineRepository repository) {
		super(repository, LineDto.class, Line.class);
	}

}
