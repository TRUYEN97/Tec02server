package com.tec02.model.dto;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CreateableDto<U> extends IdNameDto{
	protected Instant createTime;
	protected U createBy; 
	
	public String getCreateTime(){
		if(createTime == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(Date.from(createTime));
	}
}
