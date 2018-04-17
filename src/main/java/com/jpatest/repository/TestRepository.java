package com.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpatest.entity.Test;

public interface TestRepository extends JpaRepository<Test, String>{
	
}
