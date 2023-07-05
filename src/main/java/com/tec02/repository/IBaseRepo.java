package com.tec02.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepo<T> extends JpaRepository<T, Long> {
	Optional<T> findOneByName(String name);

	boolean existsByName(String name);

	List<T> findAllByNameLike(String name);

}
