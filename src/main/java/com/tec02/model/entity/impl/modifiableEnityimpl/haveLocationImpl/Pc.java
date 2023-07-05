package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl;

import java.util.HashSet;
import java.util.Set;

import com.tec02.model.entity.PcInformation;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocation;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.Program;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "pc")
public class Pc extends haveLocation<User> {
	
	@OneToOne
	@JoinColumn(name = "pcInformation_id")
	private PcInformation pcInformation;
	
	@ManyToMany
	@JoinTable(name = "pc_program",
	joinColumns = @JoinColumn(name = "pc_id"),
	inverseJoinColumns = @JoinColumn(name = "program_id"))
	private Set<Program> pcPrograms = new HashSet<>();
	
	public void addProgram(Program group) {
		this.pcPrograms.add(group);
	}
	
	public void removeProgram(Program group) {
		this.pcPrograms.remove(group);
	}
}
