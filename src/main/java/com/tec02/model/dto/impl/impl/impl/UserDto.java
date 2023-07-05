package com.tec02.model.dto.impl.impl.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.impl.impl.BaseModifiableDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserDto extends BaseModifiableDto<String> {
	private String userId;
	private boolean userstatus;
	private Set<String> userRoles = new HashSet<>();
}
