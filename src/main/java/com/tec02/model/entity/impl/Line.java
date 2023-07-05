package com.tec02.model.entity.impl;

import java.util.HashSet;
import java.util.Set;

import com.tec02.model.entity.Createable;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;

import jakarta.persistence.Entity;
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
@Table(name = "line")
public class Line extends Createable<User>{
	
	@OneToMany(mappedBy = "line")
	private Set<Location> locations = new HashSet<>();
	
	public void addLocation(Location location) {
		this.locations.add(location);
	}
	
	public void removeLocation(Location location) {
		this.locations.remove(location);
	}
}
