package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.HaveDiscription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "filegroup")
public class FileGroup extends HaveDiscription{
	
	@Column(name = "name", nullable = false, updatable = false)
	private String name;
	
	@OneToMany(mappedBy = "fileGroup")
	private Set<File> files = new HashSet<>();
	
	public void setFile(File file) {
		if(file == null) {
			return;
		}
		this.files.add(file);
	}
	
	public void removeFile(File file) {
		if(file == null) {
			return;
		}
		this.files.remove(file);
	}
	
	
	@ManyToMany
	@JoinTable(name = "group_program",
	joinColumns = @JoinColumn(name = "group_id"),
	inverseJoinColumns = @JoinColumn(name = "program_id"))
	private List<Program> programs = new ArrayList<>();
	
	public void setProgram(Program program) {
		if(program == null) {
			return;
		}
		this.programs.add(program);
	}
	
	public void removeProgram(Program program) {
		if(program == null) {
			return;
		}
		this.programs.remove(program);
	}
}
