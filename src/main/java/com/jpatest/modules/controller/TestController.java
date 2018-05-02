package com.jpatest.modules.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpatest.modules.models.Test;
import com.jpatest.modules.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Yuan
 */
@RestController
@RequestMapping("/test")
@Api(tags= {"测试jpa"})
public class TestController {
	
	@Autowired
	private ValueOperations<String,Object> valueOperations;
	
	@Autowired
	private TestService testService;
	
	@ApiOperation("查询")
	@GetMapping("/list")
	public Page<Test> list(int page,int size){
		
		Page<Test> list= testService.getList(page, size);
		
		return list;
	}
	
	@PostMapping("/add")
	@ApiOperation("新增")
	public int add(Test test){
		
		//valueOperations.set("1222",test);
		
		return testService.add(test);
	}
	
	@PostMapping("/get/{userId}")
	@ApiOperation("精确查询")
	public Optional<Test> get(@PathVariable("userId") int userId){
		
		Optional<Test> t=testService.getById(userId);
		
		return t;
	}
	
	@PostMapping("/search")
	@ApiOperation("按名称查询")
	public Iterable<Test> search(String name){
		
		Iterable<Test> t=testService.getByName(name);
		
		return t;
	}
	
	@PostMapping("/getBySql/{userId}")
	@ApiOperation("精确查询")
	public Test getBySql(@PathVariable("userId") int userId){
		
		return testService.getByQueryId(userId);
	}
	
	@ApiOperation("自定义sql查询")
	@GetMapping("/listBysql")
	public Page<Test> testSQL(int page,int size,int id){
		
		Page<Test> list= testService.getListBySql(page, size, id);
		
		return list;
	}
	

	@GetMapping("/delete/{id}")
	@ApiOperation("精确删除")
	public boolean deleteById(@PathVariable("id") int id){
		
		testService.deleteById(id);
		
		return true;
	}

}