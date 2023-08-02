package com.tec02.model.dto.impl.impl.impl.impl;

import com.tec02.model.dto.impl.impl.impl.HaveLocationDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class HaveDiscriptionDto extends HaveLocationDto<String>{
	protected String description;
}
