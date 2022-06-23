package com.epam.esm.config.profile;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@Profile("prod")
@PropertySource("classpath:database.properties")
public class ProdConfig {

    private static final String DB_DRIVER = "db.driverClassName";
    private static final String DB_URL = "db.url";
    private static final String DB_USER_NAME = "db.user";
    private static final String DB_PASSWORD = "db.password";

    private final Environment environment;

    @Autowired
    public ProdConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty(DB_DRIVER));
        dataSource.setUrl(environment.getProperty(DB_URL));
        dataSource.setUsername(environment.getProperty(DB_USER_NAME));
        dataSource.setPassword(environment.getProperty(DB_PASSWORD));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
