package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.impl.impl.impl.impl.PcDto;
import com.tec02.model.entity.PcInformation;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.Pc;
import com.tec02.repository.impl.PcRepository;
import com.tec02.util.ModelMapperUtil;

@Service
public class PcService extends BaseService<PcDto, Pc> {

	protected PcService(PcRepository repository) {
		super(repository, PcDto.class, Pc.class);
	}

	@Autowired
	private PcRepository pcRepository;

	public Pc updateInfo(Long pcID, PcInformation pcInfo) throws Exception {
		Pc pc = this.findOne(pcID);
		PcInformation pcInfoOld = pc.getPcInformation();
		ModelMapperUtil.update(pcInfo, pcInfoOld);
		pc.setPcInformation(pcInfoOld);
		return this.pcRepository.save(pc);
	}

	public List<PcDto> findAllByLocation(List<Location> locations, String name) {
		name = String.format("%%%s%%", name == null ? "" : name);
		List<Pc> entitys = this.pcRepository.findAllByLocationIn(locations, name);
		if (entitys == null) {
			return null;
		}
		return ModelMapperUtil.mapAll(entitys, dtoClass);
	}

}
