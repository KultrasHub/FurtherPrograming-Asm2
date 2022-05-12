package Backend.Project.TaxiCompany.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private ZonedDateTime createdDate;

    //booking should be linked with customer, driver, car
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;


    public Booking(long id, ZonedDateTime createdDate, Customer customer, Driver driver, Car car, Invoice invoice) {
        this.id = id;
        this.createdDate = createdDate;
        this.customer = customer;
        this.driver = driver;
        this.car = car;
        this.invoice = invoice;
    }

    public Booking() {
        this.createdDate=ZonedDateTime.now();
    }

    //Builder
    public Booking setCustomer(Customer c)
    {
        customer=c;
        return  this;
    }
    public Booking setDriver(Driver d)
    {
        driver=d;
        return this;
    }
    public Booking setCar(Car c)
    {
        car=c;
        return  this;
    }
    public Booking setCreatedDate(ZonedDateTime dateTime)
    {
        createdDate=dateTime;
        return this;
    }
    //getter
    public long getId() {
        return id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public Car getCar() {
        return car;
    }

}
