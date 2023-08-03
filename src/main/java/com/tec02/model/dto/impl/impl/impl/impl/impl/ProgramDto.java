package com.tec02.model.dto.impl.impl.impl.impl.impl;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.impl.impl.impl.impl.HaveDiscriptionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
public class ProgramDto extends HaveDiscriptionDto {

	private String password;
	
	private boolean awaysUpdate;
	
	private String fileProgram;
	
	private boolean enable;

	
}
