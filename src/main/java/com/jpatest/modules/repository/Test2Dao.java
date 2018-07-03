package com.jpatest.modules.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.jpatest.modules.models.Test2;

/**
 * @author Yuan
 *
 */
public interface Test2Dao extends JpaRepository<Test2, Integer>,QuerydslPredicateExecutor<Test2>{
	
	
}
