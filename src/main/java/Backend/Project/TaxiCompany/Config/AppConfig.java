package Backend.Project.TaxiCompany.Config;


import Backend.Project.TaxiCompany.Model.*;
import Backend.Project.TaxiCompany.Service.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
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
    //Entity creation
    @Bean
    @Scope("prototype")
    public Booking booking()
    {
        return new Booking();
    }
    @Bean
    @Scope("prototype")
    public Car car()
    {
        return new Car();
    }
    @Bean
    @Scope("prototype")
    public Customer customer()
    {
        return new Customer();
    }
    @Bean
    @Scope("prototype")
    public Driver driver()
    {
        return new Driver();
    }
    @Bean
    @Scope("prototype")
    public Invoice invoice()
    {
        return new Invoice();
    }
    //Service
    @Bean
    public BookingService bookingService(){ return  new BookingService();}
    @Bean
    public  CarService carService(){return  new CarService();}
    @Bean
    public  CustomerService customerService(){return  new CustomerService();}
    @Bean
    public DriverService driverService(){return  new DriverService();}
    @Bean
    public  InvoiceService invoiceService(){return new InvoiceService();}
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
    //--Decomment this area to run Post SQL
        //properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        dataSource.setDriverClassName("org.postgresql.Driver ");
//        dataSource.setUrl("jdbc:postgresql://localhost:3306/codeJava");
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
    //Update this for mysql database
        // properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        // dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        // dataSource.setUrl("jdbc:mysql://localhost:3306/codeJava");
        // dataSource.setUsername("root");
        // dataSource.setPassword("123456789kul000");
        //To use postgresql
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/codeJava");
        dataSource.setUsername("postgres");
        dataSource.setPassword("pass");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.setPackagesToScan("Backend.Project.TaxiCompany.Model");
        return sessionFactoryBean;
    }
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);

        return tx;
    }
}
