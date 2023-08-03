package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl;

import java.time.Instant;

import org.springframework.data.annotation.LastModifiedDate;

import com.tec02.model.entity.Createable;
import com.tec02.model.entity.IHaveLocation;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "pc")
public class Pc extends Createable<User> implements IHaveLocation {
	
	@LastModifiedDate
	@Column(name = "modifytime", nullable = false)
	protected Instant modifyTime;
	
	@ManyToOne
	@JoinColumn(name = "location_id")
	protected Location location;
	
	@Column(name = "os")
	private String os;
	
	@Column(name = "mac")
	private String mac;
	
	@Column(name = "ip")
	private String ip;

}
