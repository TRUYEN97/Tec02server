package com.tec02.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	
@MappedSuperclass
public abstract class IdNameEntity extends IdEntity{

	@Column(name = "name", nullable = false)
	protected String name;
	
	@Override
	public String toString() {
		return name;
	}
}
