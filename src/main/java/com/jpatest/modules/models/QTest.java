package com.jpatest.modules.models;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import java.sql.Types;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.spatial.RelationalPathSpatial;



/**
 * QTest is a Querydsl query type for Test
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QTest extends RelationalPathSpatial<Test> {

    private static final long serialVersionUID = 1504314977;

    public static final QTest test = new QTest("test");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final com.querydsl.sql.PrimaryKey<Test> primary = createPrimaryKey(id);

    public QTest(String variable) {
        super(Test.class, forVariable(variable), "null", "test");
        addMetadata();
    }

    public QTest(String variable, String schema, String table) {
        super(Test.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTest(String variable, String schema) {
        super(Test.class, forVariable(variable), schema, "test");
        addMetadata();
    }

    public QTest(Path<? extends Test> path) {
        super(path.getType(), path.getMetadata(), "null", "test");
        addMetadata();
    }

    public QTest(PathMetadata metadata) {
        super(Test.class, metadata, "null", "test");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(name, ColumnMetadata.named("name").withIndex(2).ofType(Types.VARCHAR).withSize(255));
    }

}

