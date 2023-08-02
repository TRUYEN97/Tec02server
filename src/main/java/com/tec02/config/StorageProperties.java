package com.tec02.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.tec02.util.Util;

import lombok.Getter;

@ConfigurationProperties("storage")
@Component
@Getter
public class StorageProperties {
	private Path appLocation = Path.of("D:/app");
	private Path dataLocation = Path.of("D:/data");
	
	public Path resolveFile(String path) {
		Path temp = Path.of(path);
		if(!temp.startsWith(appLocation)) {
			Util.checkDir(path);
			temp = appLocation.resolve(temp);
		}
		return temp;
	}
	
	public Path resolveData(String path) {
		Path temp = Path.of(path);
		if(!temp.startsWith(dataLocation)) {
			Util.checkDir(path);
			temp = dataLocation.resolve(temp);
		}
		return temp;
	}

}
