package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl;

import java.util.ArrayList;
import java.util.List;

import com.tec02.model.entity.impl.VersionProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.HaveDescription;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fileprogram")
public class FileProgram extends HaveDescription{
	
	@OneToMany(mappedBy = "fileProgram", cascade = CascadeType.ALL)
	@OrderBy("name DESC")
	private List<VersionProgram> versions = new ArrayList<>();
	
	@Column(name = "path")
	private String path;
	
	@OneToMany(mappedBy = "fileProgram")
	private List<Program> programs;
}
