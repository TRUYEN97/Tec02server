package com.tec02.api.v1.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tec02.Service.impl.impl.FileProgramService;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.VersionProgramDto;
import com.tec02.model.dto.impl.impl.impl.FileDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.FileProgramDto;
import com.tec02.model.dto.updownload.UploadFileRequest;
import com.tec02.model.dto.updownload.impl.impl.DownloadFileResponse;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;

@RestController
@RequestMapping("/api/v1/fileProgram")
public class FileProgramApi extends BaseApiV1Location<FileProgramDto, FileProgram>{
	
	protected FileProgramApi(FileProgramService service) {
		super(service);
	}

	@Autowired
	private FileProgramService fileProgramService;

	@PostMapping
	public ResponseEntity<ResponseDto> create(@RequestParam(value = "pName", required = false) String pName,
			@RequestParam(value = "sName", required = false) String sName,
			@RequestParam(value = "lName", required = false) String lName,
			@ModelAttribute("entity") UploadFileRequest entity,
			@RequestPart("file") MultipartFile file) {
		try {
			FileProgramDto dto = fileProgramService.upload(pName, sName, lName, entity, file);
			return ResponseDto.toResponse(true, dto, "Save succeed!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/version")
	public ResponseEntity<ResponseDto> findFileVersions(@RequestParam("id") Long id) {
		try {
			List<VersionProgramDto> dtos = fileProgramService.getVersionsDto(id);
			return ResponseDto.toResponse(true, dtos, "Version of id= %s", id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping
	public ResponseEntity<ResponseDto> find(@RequestParam("id") Long id) {
		try {
			FileProgramDto dtos = fileProgramService.findOneDto(id);
			return ResponseDto.toResponse(true, dtos, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("id") Long id,
			@RequestParam(name="version", required = false) String version) {
		try {
			DownloadFileResponse fileResponse = this.fileProgramService.downloadFile(id, version);
			Resource resource = fileResponse.getResource();
			return ResponseDto.toDownloadResponse(fileResponse.getHeaders(), resource);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseDto> delete(@RequestParam(value = "id", required = false) Long... ids) {
		try {
			this.fileProgramService.deleteFiles(ids);
			return ResponseDto.toResponse(true, ids, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping
	public ResponseEntity<ResponseDto> updateFilePath(@RequestBody UploadFileRequest entity) {
		try {
			FileDto dto = fileProgramService.updateFilePath(entity);
			return ResponseDto.toResponse(true, dto, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@Override
	protected List<FileProgramDto> findAllByLocation(List<Location> locations, String name) {
		return this.fileProgramService.findAllByLocation(locations, name);
	}

}
