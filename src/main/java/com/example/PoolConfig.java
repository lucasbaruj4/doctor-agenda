package com.example;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PoolConfig {
    @Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/agenda_doctor");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");
        return dataSourceBuilder.build();
    }


    // spring.datasource.hikari.connection-timeout=20000
    // spring.datasource.hikari.maximum-pool-size=20
    // spring.datasource.hikari.minimun-idle=5
    // spring.datasource.hikari.idle-timeout=300000
    // spring.datasource.hikari.pool-name=HikariCP
    // spring.datsource.hikari.max-lifetime=600000

    // private static final String URL = "jdbc:postgresql://localhost:5432/agenda_doctor";
    // private static final String USER = "postgres";
    // private static final String PASSWORD = "postgres";

    // public static Connection getConnection() {
    //     Connection connection = null;
    //     try {
    //         connection = DriverManager.getConnection(URL, USER, PASSWORD);
    //         System.out.println("Conexión exitosa a la base de datos");
    //     } catch (SQLException e) {
    //         System.err.println("Error al conectar a la base de datos: " + e.getMessage());
    //     }
    //     return connection;
    // }
}
