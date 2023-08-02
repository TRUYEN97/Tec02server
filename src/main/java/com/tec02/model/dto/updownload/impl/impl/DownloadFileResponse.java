package com.tec02.model.dto.updownload.impl.impl;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.tec02.model.dto.updownload.impl.FileVersionPathDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class DownloadFileResponse extends FileVersionPathDto{
	private HttpHeaders headers;
	private Resource resource;
}
