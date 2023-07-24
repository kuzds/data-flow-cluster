package ru.kuzds.userflow.camel.web2db.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix="spring.datasource")
@Data
public class DataSourceConfig {

    private String url;
    private String driverClassName;
    private String username;
    private String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(url)
            .driverClassName(driverClassName)
            .username(username)
            .password(password)
            .build();
    }
}