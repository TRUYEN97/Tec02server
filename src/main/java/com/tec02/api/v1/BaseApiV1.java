package com.tec02.api.v1;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.IdNameDto;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.entity.IdNameEntity;
import com.tec02.util.Util;
public abstract class BaseApiV1<D extends IdNameDto, E extends IdNameEntity> {

	private final BaseService<D, E> service;
	
	protected BaseApiV1(BaseService<D, E> service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<ResponseDto> create(@RequestBody D dto) {
		try {
			String name = dto.getName();
			if (name == null || name.isBlank()) {
				return ResponseDto.toResponse(false, null, "Name must not be null or empty!");
			}
			if (this.service.existsByName(name)) {
				return ResponseDto.toResponse(false, null, "Name(%s) has exists!", name);
			}
			D savedDto = this.service.update(dto.getId(), dto);
			if (savedDto == null) {
				return ResponseDto.toResponse(false, null, "Save %s failed!", name);
			}
			return ResponseDto.toResponse(true, savedDto, "Save %s succeed!", name);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<ResponseDto> find(@RequestParam(value = "id", required = false) List<Long> ids,
			@RequestBody(required = false) RequestDto requestDto) {
		try {
			List<D> dtos;
			if (!Util.isNullorEmpty(ids)) {
				dtos = this.service.findAllDtoByIds(ids);
			} else if (requestDto != null && !Util.isNullorEmpty(requestDto.getIds())) {
				dtos = this.service.findAllDtoByIds(requestDto.getIds());
			} else {
				dtos = this.service.findAllDto();
			}
			if (dtos == null || dtos.isEmpty()) {
				return ResponseDto.toResponse(false, null, "Not found!");
			}
			return ResponseDto.toResponse(true, dtos, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@GetMapping("/page")
	public ResponseEntity<ResponseDto> findPage(Pageable pageable) {
		try {
				 List<D> dtos = this.service.findAllDto(pageable);
			if (dtos == null || dtos.isEmpty()) {
				return ResponseDto.toResponse(false, null, "Not found!");
			}
			return ResponseDto.toResponse(true, dtos, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseDto> delete(@RequestParam(value = "id", required = false) Long... ids) {
		try {
			if (ids != null) {
				for (Long i : ids) {
					service.delete(i);
				}
				return ResponseDto.toResponse(true, ids, "delete ok");
			} else {
				return ResponseDto.toResponse(false, null, "Nothing to delete");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
}
