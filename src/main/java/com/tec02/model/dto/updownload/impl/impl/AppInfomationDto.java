package com.tec02.model.dto.updownload.impl.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.updownload.impl.AppFileInfomationDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class AppInfomationDto {
	private Long id;
	private String name;
	private String password;
	private String description;
	private boolean alwaysRun;
	private boolean alwaysUpdate;
	private boolean enable;
	private AppFileInfomationDto fileProgram;
	private Map<Long, AppFileInfomationDto> files = new HashMap<>();
}
