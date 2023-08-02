package com.tec02.model.entity.impl.modifiableEnityimpl;

import java.util.ArrayList;
import java.util.List;

import com.tec02.model.entity.impl.BaseModifiableEnity;
import com.tec02.model.entity.impl.Version;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "file")
public class File extends BaseModifiableEnity<User> {
	
	@OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
	@OrderBy("name DESC")
	private List<Version> versions = new ArrayList<>();
	
	public void addVersion(Version version) {
		versions.add(version);
	}
	
	@Column(name = "path")
	private String path;
	
	@ManyToOne
	@JoinColumn(name = "filegroup", updatable = false)
	private FileGroup fileGroup;

}
