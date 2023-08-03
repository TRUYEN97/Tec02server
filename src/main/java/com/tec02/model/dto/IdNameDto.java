package com.tec02.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdNameDto implements IidNameDto{
	protected Long id;
	protected String name;

	public String toString() {
		return name;
	}
}
