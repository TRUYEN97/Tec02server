package com.tec02.api.v1.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.Service.impl.impl.FileService;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.UploadFileRequest;
import com.tec02.model.dto.impl.VersionDto;
import com.tec02.model.dto.impl.impl.impl.FileDto;

@RestController
@RequestMapping("/api/v1/file")
public class FileApi{

	@Autowired
	private FileService fileService;
	
	@PostMapping
	public ResponseEntity<ResponseDto> create(@RequestPart("file") MultipartFile file,
			@ModelAttribute("entity") UploadFileRequest entity) {
		try {
			FileDto dto = fileService.upload(entity, file);
			return ResponseDto.toResponse(true, dto, "Save succeed!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping("/version")
	public ResponseEntity<ResponseDto> findFileVersions(@RequestParam("id") Long id) {
		try {
			List<VersionDto> dtos = fileService.getVersionsDto(id);
			return ResponseDto.toResponse(true, dtos, "Version of id= %s", id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<ResponseDto> findFiles(@RequestParam("id") Long id) {
		try {
			List<FileDto> dtos = fileService.findAllByFileGroupIdDto(id);
			return ResponseDto.toResponse(true, dtos, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
}
