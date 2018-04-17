package com.jpatest.models;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Test is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
@Entity
public class Test {

	@Id
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
         return "id = " + id + ", name = " + name;
    }

}

