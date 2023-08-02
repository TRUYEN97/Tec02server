package com.tec02.model.dto.impl;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.CreateableDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class VersionProgramDto extends CreateableDto<String>{
	private String path;
	private String description;
	private String md5;
	private String fileProgram;
	private boolean enable;
}
