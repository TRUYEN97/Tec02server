package com.tec02.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@ConfigurationProperties("storage")
@Component
@Getter
public class StorageProperties {
	private String appLocation = "D:/app";
	private String dataLocation = "D:/data";
	
	public Path resolveFile(String path) {
		if(path.startsWith(appLocation)) {
			return Path.of(path);
		}
		return Path.of(String.format("%s/%s", appLocation, path));
	}

}
