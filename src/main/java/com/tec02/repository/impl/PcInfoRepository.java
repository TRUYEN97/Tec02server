package com.tec02.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tec02.model.entity.PcInformation;

public interface PcInfoRepository extends JpaRepository<PcInformation, Long> {

}
