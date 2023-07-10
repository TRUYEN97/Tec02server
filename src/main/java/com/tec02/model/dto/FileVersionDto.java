package com.tec02.model.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@NoArgsConstructor
@ToString
public class FileVersionDto {
	private String name;
	private String path;
	private String localPath;
	private String md5;
	private String description;
}
