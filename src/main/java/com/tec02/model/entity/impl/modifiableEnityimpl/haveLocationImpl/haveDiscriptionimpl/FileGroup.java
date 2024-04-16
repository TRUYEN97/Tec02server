package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.HaveDescription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class FileGroup extends HaveDescription {
	
	@OneToMany(mappedBy = "fileGroup")
	private Set<File> files = new HashSet<>();
	
	@Column(name = "path")
	private String path;
	
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

	@ManyToMany(mappedBy = "groupFiles", fetch = FetchType.EAGER)
	private Set<Program> programs = new HashSet<>();
	
	public void addProgram(Program program) {
		if(program == null) {
			return;
		}
		program.addGroupFile(this);
		this.programs.add(program);
	}
	
	public void removeProgram(Program program) {
		if(program == null) {
			return;
		}
		program.removeGroupFile(this);
		this.programs.remove(program);
	}
	public void addAllProgram(List<Program> programs) {
		if(programs == null) {
			return;
		}
		for (Program program : programs) {
			addProgram(program);
		}
	}
	
	public void removeAllProgram(List<Program> programs) {
		if(programs == null) {
			return;
		}
		for (Program program : programs) {
			removeProgram(program);
		}
	}

	public void removeAllProgram() {
		if(programs == null || programs.isEmpty()) {
			return;
		}
		for (Program program : programs) {
			program.removeGroupFile(this);
		}
		programs.clear();
	}
}
