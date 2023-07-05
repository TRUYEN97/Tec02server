package com.tec02.api.v1;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.Service.FileSystemStorageService;
import com.tec02.model.dto.ResponseDto;

@Controller
@RequestMapping("api/v1/storage")
public class FileController {

	@Autowired
	private FileSystemStorageService storageService;

//	@PostMapping
//	public ResponseEntity<ResponseDto> handleFileUpload(@RequestParam("file") MultipartFile file) {
//		try {
//			storageService.storeFile(file);
//			return ResponseDto.toResponse(true, null, "File uploaded successfully.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseDto.toResponse(true, null, "File uploaded failed. %s", e.getLocalizedMessage());
//		}
//	}
//
//	@GetMapping("/download")
//	public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename) {
//		try {
//			byte[] fileContent = storageService.loadAsResource(filename).getContentAsByteArray();
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//			headers.setContentDispositionFormData("attachment", filename);
//			return ResponseEntity.ok().headers(headers).body(fileContent);
//		} catch (IOException e) {
//			return ResponseEntity.badRequest().body(null);
//		}
//	}
}
