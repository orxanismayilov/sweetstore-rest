package com.orxan.sweetstorerest.config;

import com.orxan.sweetstorerest.util.LoadPropertyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.orxan.sweetstorerest")
public class SpringJdbcConfig {
    private static String DB_PROPERTY_URL ="C:\\Users\\Orxan\\Desktop\\projects\\sweetstore-rest\\src\\main\\resources\\properties\\db.properties";
    private static Properties dbProperties;

    @Bean
    public DataSource mysqlDataSource() {
        dbProperties= LoadPropertyUtil.loadPropertiesFile(DB_PROPERTY_URL);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbProperties.getProperty("localedb.driver"));
        dataSource.setUrl(dbProperties.getProperty("localedb.url"));
        dataSource.setUsername(dbProperties.getProperty("localedb.user"));
        dataSource.setPassword(dbProperties.getProperty("localedb.password"));

        return dataSource;
    }
}
