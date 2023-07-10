package com.tec02.model.dto;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class DownloadFileResponse extends FileVersionDto{
	private HttpHeaders headers;
	private Resource resource;
}
