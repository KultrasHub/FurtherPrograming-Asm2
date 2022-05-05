package Backend.Project.TaxiCompany;

import Backend.Project.TaxiCompany.Config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication

public class TaxiCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiCompanyApplication.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	}

}
