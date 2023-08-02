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
public class FileProgramDto extends HaveDiscriptionDto {
	private String path;
}
