package com.tec02.model.dto.updownload;

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
	private Long parentId;
	private Boolean enable;
	private String description;
	private String version;
	private String name;
	private String dir;

}
