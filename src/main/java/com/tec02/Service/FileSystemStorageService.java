package com.tec02.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.config.StorageProperties;

@Service
public class FileSystemStorageService {

	@Autowired
	private StorageProperties properties;

	public String storeFile(MultipartFile file, String path) {
		return store(file, this.properties.resolveFile(path));
	}
	
	public String storeProgram(MultipartFile file, String path) {
		return store(file, this.properties.resolveProgram(path));
	}

	private String store(MultipartFile file, Path path) {
		try (InputStream inputStream = file.getInputStream()){
			if (!Files.exists(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
			return path.toString();
		} catch (Exception e) {
			if (path != null) {
				path.toFile().deleteOnExit();
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

	public void deleteFile(String path) throws Exception {
		File file = this.properties.resolveFile(path).toFile();
		if (file.exists() && !file.delete()) {
			throw new Exception(String.format("Delete file failed! %s", path));
		}
		clearStore(file);
	}
	
	public void deleteProgram(String path) throws Exception {
		File file = this.properties.resolveProgram(path).toFile();
		if (file.exists() && !file.delete()) {
			throw new Exception(String.format("Delete data failed! %s", path));
		}
		clearStore(file);
	}

	private void clearStore(File file) {
		File perent = file.getParentFile();
		if (perent != null) {
			String[] children = perent.list();
			if (children == null || children.length == 0) {
				perent.delete();
				clearStore(perent);
			}
		}
	}
}
