package com.jpatest.modules.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.jpatest.modules.models.Test;

public interface TestDao extends JpaRepository<Test, Integer>,QuerydslPredicateExecutor<Test>{
	
}
