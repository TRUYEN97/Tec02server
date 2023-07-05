package com.tec02.model.dto.impl.impl.impl;

import java.util.List;

import com.tec02.model.dto.impl.impl.BaseModifiableDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto extends BaseModifiableDto<String>{
	private String path;
	private String fileGroup;
	private List<String> versions;
}
