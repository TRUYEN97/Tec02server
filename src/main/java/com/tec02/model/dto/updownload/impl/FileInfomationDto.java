package com.tec02.model.dto.updownload.impl;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.updownload.FileVersionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@NoArgsConstructor
@ToString
public class FileInfomationDto extends FileVersionDto{
	private Long id;
	private String desciption;
	private String version;
}
