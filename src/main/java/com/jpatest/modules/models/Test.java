package com.jpatest.modules.models;

import javax.annotation.Generated;

/**
 * Test is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class Test {

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

