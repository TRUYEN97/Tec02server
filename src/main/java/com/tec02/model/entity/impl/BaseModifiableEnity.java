package com.tec02.model.entity.impl;

import java.time.Instant;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tec02.model.entity.Createable;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModifiableEnity<U> extends Createable<U> {
	
	@ManyToOne
	@LastModifiedBy
	@JoinColumn(name = "modifyby")
	protected U modifyBy;
	@LastModifiedDate
	@Column(name = "modifytime", nullable = false)
	protected Instant modifyTime;
}
