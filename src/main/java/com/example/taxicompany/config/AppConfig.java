package com.example.taxicompany.config;

import com.example.taxicompany.model.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZonedDateTime;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan
public class AppConfig {
    //ENtity creation
    @Bean
    public Booking booking()
    {
        return new Booking();
    }
    @Bean
    public Car car()
    {
        return new Car();
    }
    @Bean
    public Customer customer()
    {
        return new Customer();
    }
    @Bean
    public Driver driver()
    {
        return new Driver();
    }
    @Bean
    public Invoice invoice()
    {
        return new Invoice();
    }
    //
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //To use postgresql
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/codeJava");
        dataSource.setUsername("postgres");
        dataSource.setPassword("pass");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.setPackagesToScan("com.example.taxicompany.model");
        return sessionFactoryBean;
    }
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);

        return tx;
    }
}
