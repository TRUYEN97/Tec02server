package com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl;

import com.tec02.model.entity.PcInformation;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocation;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Pc extends haveLocation<User> {
	
	@OneToOne
	@JoinColumn(name = "pcInformation_id")
	private PcInformation pcInformation;

}
