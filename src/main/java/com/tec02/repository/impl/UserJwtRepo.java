package com.tec02.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tec02.model.entity.UserJwt;

public interface UserJwtRepo extends JpaRepository<UserJwt, Long> {

}
