package com.tec02.model.acl;

import com.tec02.model.entity.IdEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "acl_entry")
public class Acl_entry extends IdEntity {
	@ManyToOne
	@JoinColumn(name = "acl_object_identity")
	private Acl_object_identity acl_object_identity;
	private int acl_order;
	@ManyToOne
	@JoinColumn(name = "sid")
	private Acl_sid acl_sid;
	private int mask;
	private boolean granting;
	private boolean audit_success;
	private boolean audit_failure;
}
