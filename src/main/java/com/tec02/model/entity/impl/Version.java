package com.tec02.model.entity.impl;

import com.tec02.model.entity.Createable;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "version")
public class Version extends Createable<User>{
	@Column(name = "path", nullable = false, updatable = false)
	private String path;
	private boolean enable;
	@Column(name = "description", nullable = false, updatable = false, columnDefinition = "nvarchar(255)")
	private String description;
	@Column(name = "md5", nullable = false, updatable = false)
	private String md5;
	@ManyToOne
	@JoinColumn(name = "file", nullable = false, updatable = false)
	private File file;
}
