package com.jpatest.modules.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.jpatest.modules.models.Test;

/**
 * @author Yuan
 *
 */
public interface TestDao extends JpaRepository<Test, Integer>,QuerydslPredicateExecutor<Test>{
	
	/**
	 * 精确查询
	 * @param id id 
	 * @return Test
	 */
	@Query(value="select id,name from test where id=?1",nativeQuery=true)
	Test querybySql(@Param("id") int id);
	
	/**
     * fetch data by rule id
     * 
     * @param id rule id
     * @param pageable page number
     * @return Page<Test>
     */
	@Query(value="select id,name from test where id=?1",nativeQuery=true,countQuery="select count(1) from test where id=?1")
	Page<Test> queryPageBySql(@Param("id") int id,Pageable pageable);
	
}
