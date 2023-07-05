package com.tec02.model.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public abstract class Createable<U> extends IdNameEntity{
	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "createby", updatable = false)
	protected U createBy;
	@CreatedDate
	@Column(name = "createtime", updatable = false, nullable = false)
	protected Instant createTime;
}
