package com.tec02.Service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.config.StorageProperties;

@Service
public class FileSystemStorageService {

	@Autowired
	private StorageProperties properties;


	public String storeFile(MultipartFile file, String path) {
		Path localPath = null;
		try {
			if (file.isEmpty()) {
				throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
			}
			localPath = this.properties.resolveFile(path);
			if(!Files.exists(localPath.getParent())) {
				Files.createDirectories(localPath.getParent());
			}
			if(Files.exists(localPath)) {
				Files.delete(localPath);
			}
			Files.copy(file.getInputStream(), localPath);
			return localPath.toString();
		} catch (Exception e) {
			if(localPath != null) {
				localPath.toFile().deleteOnExit();
			}
			throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

//	public Stream<Path> loadAll() {
//		try {
//			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
//					.map(path -> this.rootLocation.relativize(path));
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to read stored files", e);
//		}
//
//	}

//	public Path load(String filename) {
//		return rootLocation.resolve(filename);
//	}

//	public Resource loadAsResource(String filename) {
//		try {
//			Path file = load(filename);
//			Resource resource = new UrlResource(file.toUri());
//			if (resource.exists() || resource.isReadable()) {
//				return resource;
//			} else {
//				throw new RuntimeException("Could not read file: " + filename);
//
//			}
//		} catch (Exception e) {
//			throw new RuntimeException("Could not read file: " + filename, e);
//		}
//	}

//	public void deleteAll() {
//		FileSystemUtils.deleteRecursively(rootLocation.toFile());
//	}
//
//	public void init() {
//		try {
//			Files.createDirectory(rootLocation);
//		} catch (Exception e) {
//			throw new RuntimeException("Could not initialize storage", e);
//		}
//	}
}
