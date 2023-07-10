package com.tec02.model.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@ToString
public class UploadFileRequest {
	private Long id;
	private Long fgroupId;
	private String description;
	private String version;
	private String name;
	private String dir;

}
