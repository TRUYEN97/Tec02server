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
@Table(name = "acl_object_identity")
@Entity
public class Acl_object_identity extends IdEntity{
	@ManyToOne
	@JoinColumn(name = "object_id_class")
	private Acl_class object_id_class;

	
	private Long object_id_identity;
	
	@ManyToOne
	@JoinColumn(name = "parent_object")
	private Acl_object_identity parent_object;
	
	@ManyToOne
	@JoinColumn(name = "owner_sid")
	private Acl_sid owner_sid;
	
	private boolean entries_inheriting;
	
}
