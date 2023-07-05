package com.tec02.api.v1.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.LineService;
import com.tec02.api.v1.BaseApiV1;
import com.tec02.model.dto.impl.LineDto;
import com.tec02.model.entity.impl.Line;

@RestController
@RequestMapping("api/v1/line")
public class LineAPI extends BaseApiV1<LineDto, Line> {
	protected LineAPI(LineService service) {
		super(service);
	}
	
}
