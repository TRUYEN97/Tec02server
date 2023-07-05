package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.HaveDiscription;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.Pc;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "program")
public class Program extends HaveDiscription{
	
	
	@ManyToMany(mappedBy = "programs")
	private List<FileGroup> groupFiles = new ArrayList<>();
	
	public void addGroupFile(FileGroup groupFile) {
		this.groupFiles.add(groupFile);
	}

	public void removeGroupFile(FileGroup groupFile) {
		this.groupFiles.remove(groupFile);
	}
	
	@ManyToMany(mappedBy = "pcPrograms")
	private Set<Pc> pcs = new HashSet<>();

	public void addPc(Pc pc) {
		this.pcs.add(pc);
	}

	public void removePc(Pc pc) {
		this.pcs.remove(pc);
	}
}
