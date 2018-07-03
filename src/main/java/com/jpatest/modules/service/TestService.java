package com.jpatest.modules.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.jpatest.modules.models.Test;
import com.jpatest.modules.models.TestVo;

/**
 * @author Yuan
 */
public interface TestService {
	
	public int add(Test test);
	
	public Optional<Test> getById(int id);
	
	public Page<Test> getList(int page,int size);
	
	public List<Test> getByName(String name);
	
	public Test getByQueryId(int id);
	
	public Page<Test> getListBySql(int page,int size,int id);
	
	void deleteById(int id);
	
	TestVo getTestVoById(int id);

}
