package com.tec02.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperUtil {
	private final ModelMapper mapper;
	
	
	private ModelMapperUtil() {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	public static <T> T map(final Object source, Class<T> clazz ) {
		return new ModelMapperUtil().mapper.map(source, clazz);
	}
	
	public static void update(final Object source, Object target) {
		ModelMapperUtil mapperUtil = new ModelMapperUtil();
		mapperUtil.mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		mapperUtil.mapper.map(source, target);
	}
	
	public static <T, E> List<T> mapAll(final Collection<E> listSource, Class<T> clazz ){
		return listSource.stream().map(source -> map(source, clazz)).collect(Collectors.toList());
	}
	
	
}
