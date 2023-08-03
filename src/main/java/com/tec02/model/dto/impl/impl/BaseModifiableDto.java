package com.tec02.model.dto.impl.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import com.tec02.model.dto.CreateableDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseModifiableDto<U> extends CreateableDto<U> {
	protected U modifyBy;
	protected Instant modifyTime;
	public String getModifyTime(){
		if(modifyTime == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(Date.from(modifyTime));
	}
}
