package com.jpatest.modules.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.jpatest.modules.models.Test;

public interface TestDao extends JpaRepository<Test, Integer>,QuerydslPredicateExecutor<Test>{
	
	@Query(value="select id,name from test where id=?1",nativeQuery=true)
	Test QuerybySql(@Param("id") int id);
	
	@Query(value="select id,name from test where id=?1",nativeQuery=true,countQuery="select count(1) from test where id=?1")
	Page<Test> QueryPageBySql(@Param("id") int id,Pageable pageable);
	
}
