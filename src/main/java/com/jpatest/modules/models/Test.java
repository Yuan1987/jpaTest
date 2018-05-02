package com.jpatest.modules.models;

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
		StringBuilder builder = new StringBuilder();
		builder.append("Test [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}

