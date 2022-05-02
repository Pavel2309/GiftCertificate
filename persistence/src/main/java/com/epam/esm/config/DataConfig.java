package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:config/db.properties")
@Profile("production")
public class DataConfig {
    @Bean
    public DataSource dataSource(@Value("${driverClassName}") String driver,
                                 @Value("${jdbcUrl}") String jdbcUrl,
                                 @Value("${db.username}") String username,
                                 @Value("${password}") String password,
                                 @Value("${maximumPoolSize}") int maximumPoolSize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maximumPoolSize);
        return new HikariDataSource(config);
    }
}
