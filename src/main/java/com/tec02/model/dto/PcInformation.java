package com.tec02.model.dto;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import com.tec02.model.dto.impl.impl.LocationDto;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class PcInformation {
	private String name;
	private String os;
	private String mac;
	private String ip;
	
	protected Instant modifyTime;
	public String getModifyTime(){
		if(modifyTime == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(Date.from(modifyTime));
	}
	
	private LocationDto location;
	
	public String getName() {
		return name;
	}
	
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
}
