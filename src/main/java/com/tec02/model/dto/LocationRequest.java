package com.tec02.model.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LocationRequest {
	private Long productId;
	private Long stationId;
	private Long lineId;
	private Long locationId;
	private List<Long> targetId;
}
