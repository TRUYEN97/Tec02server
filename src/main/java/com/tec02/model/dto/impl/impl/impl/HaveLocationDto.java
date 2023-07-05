package com.tec02.model.dto.impl.impl.impl;

import com.tec02.model.dto.impl.impl.BaseModifiableDto;
import com.tec02.model.dto.impl.impl.LocationDto;

import lombok.Setter;

@Setter
public abstract class HaveLocationDto<U> extends BaseModifiableDto<U> {
	protected LocationDto location;

	public String getProduct() {
		if (location == null) {
			return null;
		}
		return location.getProduct();
	}

	public String getStation() {
		if (location == null) {
			return null;
		}
		return location.getStation();
	}

	public String getLine() {
		if (location == null) {
			return null;
		}
		return location.getLine();
	}
}
