package Backend.Project.TaxiCompany.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column
    private ZonedDateTime createdDate;//2022-05-06T12:00+07:00

    //booking should be linked with customer, driver, car
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    @JsonIgnore
    private Driver driver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @JsonIgnore
    private Car car;

    public Booking(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Booking() {

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
