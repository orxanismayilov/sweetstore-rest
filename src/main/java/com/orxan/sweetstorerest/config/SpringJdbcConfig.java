package com.orxan.sweetstorerest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.orxan.sweetstorerest")
@PropertySource("classpath:properties/db.properties")
public class SpringJdbcConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("localedb.driver"));
        dataSource.setUrl(environment.getProperty("localedb.url"));
        dataSource.setUsername(environment.getProperty("localedb.user"));
        dataSource.setPassword(environment.getProperty("localedb.password"));

        return dataSource;
    }
}
