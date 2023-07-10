package com.tec02.Service;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.config.StorageProperties;
import com.tec02.util.Util;

@Service
public class FileSystemStorageService {

	@Autowired
	private StorageProperties properties;

	public String storeFile(MultipartFile file, String path) {
		Path localPath = null;
		try {
			Util.checkDir(path);
			if (file.isEmpty()) {
				throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
			}
			localPath = this.properties.resolveFile(path);
			if (!Files.exists(localPath.getParent())) {
				Files.createDirectories(localPath.getParent());
			}
			Files.copy(file.getInputStream(), localPath, StandardCopyOption.REPLACE_EXISTING);
			return localPath.toString();
		} catch (Exception e) {
			if (localPath != null) {
				localPath.toFile().deleteOnExit();
			}
			throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Resource resource = new UrlResource(Path.of(fileName).toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex.getLocalizedMessage());
		}
	}
}
