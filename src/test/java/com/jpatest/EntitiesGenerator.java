package com.jpatest;

import java.io.File;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.codegen.BeanSerializer;
import com.querydsl.sql.codegen.MetaDataExporter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntitiesGenerator {

    @Autowired
    private DataSource dataSource;

    @Test
    public void generateEntities() {

        MetaDataExporter exporter = new MetaDataExporter();

        exporter.setBeanSerializer(new BeanSerializer());
        exporter.setBeansTargetFolder(new File("target/generated-sources/java"));
        exporter.setBeanPackageName("com.jpatest.modules.models");
        exporter.setNamePrefix("Q");
        exporter.setSpatial(true);
        exporter.setTableNamePattern("Test2");
        exporter.setPackageName("com.jpatest.modules.models");
        exporter.setTargetFolder(new File("target/generated-sources/java"));
        exporter.setExportTables(true);
        try {
            exporter.export(dataSource.getConnection().getMetaData());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
