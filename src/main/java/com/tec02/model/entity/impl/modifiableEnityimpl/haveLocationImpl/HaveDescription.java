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
public abstract class HaveDescription extends haveLocation<User> {
	@Column(name = "description", nullable = false, updatable = false, columnDefinition = "nvarchar(255)")
	protected String description;
}
