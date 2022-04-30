package com.example.taxicompany;

import com.example.taxicompany.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class TaxiCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiCompanyApplication.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);



	}


}
