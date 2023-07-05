package com.tec02.model.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UploadFileRequest {
	private Long id;
	private Long fgroundId;
	private String description;
	private String version;
	private String name;
	private String dir;
	
	public String getDir(){
		return dir == null? "":dir;
	}
}
