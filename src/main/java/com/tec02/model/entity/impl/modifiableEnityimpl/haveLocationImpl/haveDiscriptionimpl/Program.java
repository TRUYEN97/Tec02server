package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.HaveDescription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "program")
public class Program extends HaveDescription{

	@Column(name = "password", length = 20)
	private String password;
	
	@Column(name = "alwaysrun", nullable = false)
	private boolean alwaysRun;
	
	@Column(name = "alwaysupdate", nullable = false)
	private boolean alwaysUpdate;
	
	@Column(name = "enable", nullable = false)
	private boolean enable;
	
	
	@ManyToOne
	@JoinColumn(name = "fileprogram")
	private FileProgram fileProgram;
	
	@ManyToMany
	@JoinTable(name = "group_program",
	joinColumns = @JoinColumn(name = "program_id"),
	inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<FileGroup> groupFiles = new HashSet<>();
	
	public void addGroupFile(FileGroup groupFile) {
		this.groupFiles.add(groupFile);
	}

	public void removeGroupFile(FileGroup groupFile) {
		this.groupFiles.remove(groupFile);
	}
	
	public void removeAllGroupFile(List<FileGroup> groupFiles) {
		this.groupFiles.removeAll(groupFiles);
	}

	public void addAllGroupFile(List<FileGroup> fileGroups) {
		this.groupFiles.addAll(fileGroups);
	}

	public void removeAllGroupFileIds(List<Long> ids) {
		if(ids == null || ids.isEmpty() ||groupFiles == null || groupFiles.isEmpty()) {
			return;
		}
		List<FileGroup> removes = new ArrayList<>();
		for (FileGroup group : groupFiles) {
			if(ids.contains(group.getId())) {
				removes.add(group);
			}
		}
		groupFiles.removeAll(removes);
	}
}
