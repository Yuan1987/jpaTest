package com.jpatest.modules.models;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Test2 is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
@Entity
public class Test2 {

    @Id
    private Integer id;

    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
         return "id = " + id + ", text = " + text;
    }

}

