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
	private Path fileLocation = Path.of("C:/appServer/file");
	private Path programLocation = Path.of("C:/appServer/program");
	
	public Path resolveFile(String path) {
		Path temp = Path.of(path);
		if(!temp.startsWith(fileLocation)) {
			Util.checkDir(path);
			temp = fileLocation.resolve(temp);
		}
		return temp;
	}
	
	public Path resolveProgram(String path) {
		Path temp = Path.of(path);
		if(!temp.startsWith(programLocation)) {
			Util.checkDir(path);
			temp = programLocation.resolve(temp);
		}
		return temp;
	}

}
