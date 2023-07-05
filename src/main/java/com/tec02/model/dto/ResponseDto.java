package com.tec02.model.dto;

import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
	
	public ResponseDto(boolean result, Object data, String message, Object... param) {
		super();
		this.result = result;
		this.message = String.format(message, param);
		this.data = data;
	}
	private boolean result;
	private String message;
	private Object data;
	
	public static ResponseEntity<ResponseDto> toResponse(boolean status, Object data, String message, Object... param) {
		return ResponseEntity.ok().body(new ResponseDto(status, data, message, param));
		
	}
}
