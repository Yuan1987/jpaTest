package com.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.jpatest.models.Test;

public interface TestRepository extends JpaRepository<Test, Integer>,QuerydslPredicateExecutor<Test>{
	
}
