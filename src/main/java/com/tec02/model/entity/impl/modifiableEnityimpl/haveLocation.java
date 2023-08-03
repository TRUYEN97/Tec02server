package com.tec02.model.entity.impl.modifiableEnityimpl;

import com.tec02.model.entity.IHaveLocation;
import com.tec02.model.entity.impl.BaseModifiableEnity;
import com.tec02.model.entity.impl.Location;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class haveLocation<U> extends BaseModifiableEnity<U> implements IHaveLocation {
	@ManyToOne
	@JoinColumn(name = "location_id")
	protected Location location;
}
