package com.example.demo.domain.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoRepositoryBean
public interface BaseJpaRepository<T> extends JpaRepository<T, Long>,JpaSpecificationExecutor<T> {

	Set<T> findByIdIn(Collection<Long> ids);
	
	List<T> findAllByIdIn(Collection<Long> ids);
}
