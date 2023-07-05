package com.tec02.model.acl;

import com.tec02.model.entity.IdEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "acl_sid")
public class Acl_sid extends IdEntity {
	private boolean principal;
	private String sid;

}
