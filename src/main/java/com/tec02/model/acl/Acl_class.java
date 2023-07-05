package com.tec02.model.acl;

import com.tec02.model.entity.IdEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "acl_class")
@Entity
public class Acl_class extends IdEntity {
	
	@Column(name = "class")
	private String className;
}
