package com.tec02.model.entity;

import com.tec02.model.entity.impl.Location;

public interface IHaveLocation extends IidName {

	void setLocation(Location location);

	Location getLocation();
	
}
