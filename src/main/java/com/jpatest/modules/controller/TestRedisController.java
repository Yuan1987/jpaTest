package com.jpatest.modules.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Yuan
 */
@RestController
@RequestMapping("/test")
@Api(tags= {"测试jpa"})
public class TestRedisController {
	
	@Autowired
	private ListOperations<String, Object> listOperations;
	
	@Autowired
	private SetOperations<String, Object> setOperations;
	
	@Autowired
	private HashOperations<String, String, Object> hashOperations;
	
	
	@ApiOperation("查询")
	@GetMapping("/test")
	public String list(){
		
		listOperations.rightPush("javaList", 1);
		listOperations.rightPush("javaList", 2);
		listOperations.rightPush("javaList", 3);
		
		List<Object> range = listOperations.range("javaList", 0L, 3L);
		
		range.forEach(e->{
			
			//System.out.println(e);
			
		});
		
		setOperations.add("javaSet", 1);
		setOperations.add("javaSet", 2);
		setOperations.add("javaSet", 3);
		
		Set<Object> members = setOperations.members("javaSet");
		
		members.forEach(e->{
			System.out.println("set:"+e);
		});
		
		Object object = hashOperations.get("person", "age");
		
		System.out.println("perosn age "+object);
		
		return "";
	}
}
