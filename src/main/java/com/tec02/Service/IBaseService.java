package com.tec02.Service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tec02.model.dto.IdNameDto;
import com.tec02.model.entity.IdNameEntity;

public interface IBaseService<D extends IdNameDto, E extends IdNameEntity> {
	
	D update(Long id, D dto);
	
	D updateDto(Long id, E entity);
	
	E update(Long id, E entity);
	
	void delete(Long id);
	
	boolean exists(D dto);
	
	boolean existsByName(String name);
	
	E findOneByName(String name);
	
	D findOneDtoByName(String name);
	List<E> findAllByNameLike(String name);
	
	List<D> findAllByNameLikeDto(String name);
	
	List<D> findAllDto(Pageable pageable);
	
	List<D> findAllDtoByIds(List<Long> ids);
	
	List<D> findAllDto();
	
	List<E> findAllByIds(List<Long> ids);
	
	List<E> findAll();
	
	List<E> findAll(Pageable pageable);
	
	E findOne(Long id);

	D findOneDto(Long id);

	boolean exists(E entity);
}
