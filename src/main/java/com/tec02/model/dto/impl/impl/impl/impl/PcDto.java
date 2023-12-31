package com.tec02.model.dto.impl.impl.impl.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.CreateableDto;
import com.tec02.model.dto.IHaveLocationDto;
import com.tec02.model.dto.impl.impl.LocationDto;

import lombok.Setter;

@Setter
@Component
public class PcDto extends CreateableDto<String> implements IHaveLocationDto {
	private String os;
	private String mac;
	private String ip;
	private LocationDto location;

	public String getMac() {
		return mac;
	}

	public String getIp() {
		return ip;
	}

	public String getOs() {
		return os;
	}

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
	
	protected Instant modifyTime;
	public String getModifyTime(){
		if(modifyTime == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(Date.from(modifyTime));
	}
}
