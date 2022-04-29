package Backend.Project.TaxiCompany.Entity;

import javax.persistence.*;
import java.awt.print.Book;
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
    @Column
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;
    @Column
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
    public Booking(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
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
