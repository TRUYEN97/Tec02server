package com.tec02.Service.impl.impl;

import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.impl.LineDto;
import com.tec02.model.entity.impl.Line;
import com.tec02.repository.impl.LineRepository;

@Service
public class LineService extends BaseService<LineDto, Line> {

	protected LineService(LineRepository repository) {
		super(repository, LineDto.class, Line.class);
	}
}
