package com.tec02.model.dto.impl.impl;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.CreateableDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class LocationDto extends CreateableDto<String> {
	
	private String product;
	private String line;
	private String station;
}
