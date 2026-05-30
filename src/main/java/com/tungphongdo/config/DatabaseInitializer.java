package com.tungphongdo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DatabaseInitializer implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Get Environment from bean factory
        try {
            Environment env = beanFactory.getBean(Environment.class);
            
            String url = env.getProperty("spring.datasource.url");
            String username = env.getProperty("spring.datasource.username");
            String password = env.getProperty("spring.datasource.password");
            String driverClassName = env.getProperty("spring.datasource.driver-class-name");

            if (url != null && username != null && password != null && driverClassName != null) {
                createDatabaseIfNotExists(url, username, password, driverClassName);
            }
        } catch (Exception e) {
            System.err.println("Failed during database initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createDatabaseIfNotExists(String url, String username, String password, String driverClassName) {
        // Extract database name from URL
        String databaseName = extractDatabaseName(url);
        
        // Create root URL without database name
        String rootUrl = url.substring(0, url.lastIndexOf("/"));

        try {
            // Load driver dynamically from config (e.g. com.mysql.cj.jdbc.Driver, org.postgresql.Driver, ...)
            Class.forName(driverClassName);
            Connection conn = DriverManager.getConnection(rootUrl, username, password);
            Statement stmt = conn.createStatement();

            // Create database if not exists
            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            stmt.execute(sql);
            stmt.close();
            conn.close();

            System.out.println("Database '" + databaseName + "' created or already exists.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to create database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String extractDatabaseName(String url) {
        // Extract database name from JDBC URL
        // Format: jdbc:mysql://localhost:3306/demo_cicd_github_action?...
        int lastSlash = url.lastIndexOf("/");
        int questionMark = url.indexOf("?", lastSlash);
        
        if (questionMark > 0) {
            return url.substring(lastSlash + 1, questionMark);
        } else {
            return url.substring(lastSlash + 1);
        }
    }
}




