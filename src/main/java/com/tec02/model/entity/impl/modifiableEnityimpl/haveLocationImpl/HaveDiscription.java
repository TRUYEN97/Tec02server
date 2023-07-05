package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl;

import com.tec02.model.entity.impl.modifiableEnityimpl.User;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocation;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class HaveDiscription extends haveLocation<User> {
	@Column(name = "description", nullable = false)
	protected String description;
}
