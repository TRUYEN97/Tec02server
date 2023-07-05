package com.tec02.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "userjwt")
public class UserJwt extends IdEntity {
	@Column(name = "jwt")
	private String jwt;
}
