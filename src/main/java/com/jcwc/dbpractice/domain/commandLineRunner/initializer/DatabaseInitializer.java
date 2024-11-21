package com.jcwc.dbpractice.domain.commandLineRunner.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@Order(1)
public class DatabaseInitializer implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        executeSqlScript("db/changelog/database_clear.sql");
        executeSqlScript("db/changelog/data_init.sql");
//        executeSqlScript("db/changelog/create_indexes.sql");
    }

    private void executeSqlScript(String scriptPath) throws Exception {
        Resource resource = new ClassPathResource(scriptPath);
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
        }
    }
}

