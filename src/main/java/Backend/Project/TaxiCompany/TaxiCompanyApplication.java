package Backend.Project.TaxiCompany;

import Backend.Project.TaxiCompany.Config.AppConfig;
import Backend.Project.TaxiCompany.Model.*;
import Backend.Project.TaxiCompany.Service.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class TaxiCompanyApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
        //service
        BookingService bs=context.getBean(BookingService.class);
        CarService cars=context.getBean(CarService.class);
        CustomerService cuss=context.getBean(CustomerService.class);
        DriverService ds=context.getBean(DriverService.class);
        InvoiceService is=context.getBean(InvoiceService.class);
        //Create
        Booking b1=context.getBean(Booking.class);
        Booking b2=context.getBean(Booking.class);
        //Car
        Car car1=context.getBean(Car.class);
        Car car2=context.getBean(Car.class);
        car1.setLicensePlate("21-StormEnd");
        car2.setLicensePlate("31-KingLanding");
        //Customer
        Customer cus1= context.getBean(Customer.class);
        cus1.setName("Eddard").setAddress("12 Winterfell").setPhone("1234");
        Customer cus2=context.getBean(Customer.class);
        cus2.setName("Catelyn").setAddress("29 Winterfell").setPhone("3434");
        //Driver
        Driver d1=context.getBean(Driver.class);
        d1.setName("Jory");
        Driver d2=context.getBean(Driver.class);
        d2.setName("Black fish");
        //
        Invoice i1=context.getBean(Invoice.class);
        i1.setBooking(b1);
        Invoice i2=context.getBean(Invoice.class);
        i2.setBooking(b2);
        //set booking
        cars.saveCar(car1);
        cars.saveCar(car2);
        cuss.saveCustomer(cus1);
        cuss.saveCustomer(cus2);
        ds.saveDriver(d1);
        ds.saveDriver(d2);
        //
        b1.setCar(car1).setCustomer(cus1).setDriver(d1);
        b2.setCar(car2).setDriver(d2).setCustomer(cus2);
        //saving
        bs.saveBooking(b1);
        bs.saveBooking(b2);
        i1.setBooking(b1);
        i2.setBooking(b2);
        is.saveInvoice(i1);
        is.saveInvoice(i2);
    }

}
