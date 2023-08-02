package com.tec02.Service.impl.impl;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.IdNameDto;
import com.tec02.model.entity.IdNameEntity;
import com.tec02.repository.IBaseRepo;

public abstract class AbsSaveDefaultLocationService<D extends IdNameDto, E extends IdNameEntity>  extends BaseService<D, E>{

	@Autowired
	protected IBaseRepo<E> reponsitory;
	
	protected AbsSaveDefaultLocationService(IBaseRepo<E> repository, Class<D> dtoClass,
			Class<E> enityClass) {
		super(repository, dtoClass, enityClass);
	}

	public E getDefault() {
		E e = (E) this.findOneByName("All");
		if(e == null) {
			E newE;
			try {
				newE = enityClass.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				return null;
			}
			newE.setName("All");
			e = this.reponsitory.save(newE);
		}
		return e;
	}
	
	public E findByIdOrGetDefault(Long id) {
		E e = null;
		if (id != null) {
			e = this.reponsitory.findById(id).orElse(null);
		}else {
			e = this.getDefault();
		}
		if (e == null) {
			throw new RuntimeException(String.format( "Invalid %s", enityClass.getSimpleName()));
		}
		return e;
	}
	
	public E findByNameOrGetDefault(String name) {
		E e = null;
		if (name != null) {
			e = this.reponsitory.findOneByName(name).orElse(null);
		}else {
			e = this.getDefault();
		}
		if (e == null) {
			throw new RuntimeException(String.format( "Invalid %s", enityClass.getSimpleName()));
		}
		return e;
	}
	
}
