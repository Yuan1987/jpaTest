package com.jpatest.modules.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;

import com.querydsl.sql.spatial.RelationalPathSpatial;

import com.querydsl.spatial.*;



/**
 * QTest2 is a Querydsl query type for Test2
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QTest2 extends RelationalPathSpatial<Test2> {

    private static final long serialVersionUID = -610875919;

    public static final QTest2 test2 = new QTest2("test2");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath text = createString("text");

    public final com.querydsl.sql.PrimaryKey<Test2> primary = createPrimaryKey(id);

    public QTest2(String variable) {
        super(Test2.class, forVariable(variable), "null", "test2");
        addMetadata();
    }

    public QTest2(String variable, String schema, String table) {
        super(Test2.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTest2(String variable, String schema) {
        super(Test2.class, forVariable(variable), schema, "test2");
        addMetadata();
    }

    public QTest2(Path<? extends Test2> path) {
        super(path.getType(), path.getMetadata(), "null", "test2");
        addMetadata();
    }

    public QTest2(PathMetadata metadata) {
        super(Test2.class, metadata, "null", "test2");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(text, ColumnMetadata.named("text").withIndex(2).ofType(Types.VARCHAR).withSize(255));
    }

}

