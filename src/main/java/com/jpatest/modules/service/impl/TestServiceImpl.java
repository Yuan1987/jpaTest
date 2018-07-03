package com.jpatest.modules.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpatest.modules.models.QTest;
import com.jpatest.modules.models.Test;
import com.jpatest.modules.repository.TestDao;
import com.jpatest.modules.service.TestService;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @author Yuan
 *
 */
@Service
@CacheConfig(cacheNames = "Test")
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional
    @CachePut
    public int add(Test test) {
        
        entityManager.persist(test);
        entityManager.flush();
        entityManager.clear();

        return testDao.save(test).getId();
    }

    @Override
    @Cacheable
    public Optional<Test> getById(int id) {

        // Test test =
        // queryFactory.select(QTest.test).from(QTest.test).where(QTest.test.id.eq(id)).fetchFirst();

        Optional<Test> t = testDao.findById(id);

        return t;
    }

    @Override
    // @Cacheable
    public Page<Test> getList(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return testDao.findAll(pageable);
    }

    @Override
    @Cacheable
    public List<Test> getByName(String name) {

        QTest q = QTest.test;

        Iterable<Test> it = testDao.findAll(q.name.eq(name));

        List<Test> list = new ArrayList<>();

        it.forEach(t -> list.add(t));

        return list;
    }

    @Override
    @Cacheable
    public Test getByQueryId(int id) {
        return testDao.querybySql(id);
    }

    @Override
    @Cacheable
    public Page<Test> getListBySql(int page, int size, int id) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return testDao.queryPageBySql(id, pageable);
    }

    @Override
    @CacheEvict(key = "#p0")
    public void deleteById(int id) {
        testDao.deleteById(id);
        ;
    }

}
