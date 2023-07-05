package com.tec02.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestDto {
	private Long id;
	private List<Long> ids;
	
}
