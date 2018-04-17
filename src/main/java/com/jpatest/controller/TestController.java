package com.jpatest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpatest.entity.Test;
import com.jpatest.repository.TestRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author yuanhangbiao
 */
@RestController
@RequestMapping("/test")
@Api(tags= {"测试jpa"})
public class TestController {
	
	@Autowired
	private TestRepository testRepository;
	
	@ApiOperation("查询")
	@GetMapping("/list")
	public Page<Test> list(int page,int size){
		
		Pageable pageable= PageRequest.of(page-1, size);
		
		Page<Test> list= testRepository.findAll(pageable);
		
		return list;
	}
	
	@PostMapping("/add")
	@ApiOperation("新增")
	public Test add(Test test){
		
		Test t=testRepository.save(test);
		
		return t;
	}
	
	@PostMapping("/get/{userId}")
	@ApiOperation("新增")
	public Optional<Test> add(@PathVariable("userId") String userId){
		
		Optional<Test> t=testRepository.findById(userId);
		
		return t;
	}

}
