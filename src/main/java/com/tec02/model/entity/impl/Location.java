package com.tec02.model.entity.impl;

import java.util.HashSet;
import java.util.Set;

import com.tec02.model.entity.Createable;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.Pc;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.Program;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "location")
public class Location extends Createable<User> {

	@ManyToOne
	@JoinColumn(name = "product_id", updatable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "Line_id", updatable = false)
	private Line line;

	@ManyToOne
	@JoinColumn(name = "station_id", updatable = false)
	private Station station;

	@OneToMany(mappedBy = "location")
	private Set<Pc> pcs = new HashSet<>();

	public void addPc(Pc pc) {
		this.pcs.add(pc);
	}

	public void removePc(Pc pc) {
		this.pcs.remove(pc);
	}
	
	@OneToMany(mappedBy = "location")
	private Set<FileProgram> filePrograms = new HashSet<>();
	
	public void addFileProgram(FileProgram groupFile) {
		this.filePrograms.add(groupFile);
	}

	public void removeFileProgram(FileProgram groupFile) {
		this.filePrograms.remove(groupFile);
	}

	@OneToMany(mappedBy = "location")
	private Set<FileGroup> groupFiles = new HashSet<>();

	public void addGroupFile(FileGroup groupFile) {
		this.groupFiles.add(groupFile);
	}

	public void removeGroupFile(FileGroup groupFile) {
		this.groupFiles.remove(groupFile);
	}

	@OneToMany(mappedBy = "location")
	private Set<Program> programs = new HashSet<>();

	public void addProgram(Program program) {
		this.programs.add(program);
	}

	public void removeProgram(Program program) {
		this.programs.remove(program);
	}
}
