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

    @Column
    private String startingLocation;

    @Column
    private String endLocation;
    @Column
    private ZonedDateTime pickUpDateTime;
    @Column
    private ZonedDateTime dropOffDateTime;
    @Column
    private double distanceTrip;
    public Booking(long id, ZonedDateTime createdDate, Customer customer, Driver driver, Car car) {
        this.id = id;
        this.createdDate = createdDate;
        this.customer = customer;
        this.driver = driver;
        this.car = car;
    }

    public Booking() {
        this.createdDate=ZonedDateTime.now();
    }

    //Builder
    public Booking setId(long id)
    {
        this.id=id;
        return this;
    }
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

    public Booking setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
        return this;
    }

    public Booking setEndLocation(String endLocation) {
        this.endLocation = endLocation;
        return this;
    }

    public Booking setPickUpDateTime(ZonedDateTime pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
        return this;
    }

    public Booking setDropOffDateTime(ZonedDateTime dropOffDateTime) {
        this.dropOffDateTime = dropOffDateTime;
        return this;
    }

    public Booking setDistanceTrip(double distanceTrip) {
        this.distanceTrip = distanceTrip;
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

    public String getStartingLocation() {
        return startingLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public ZonedDateTime getPickUpDateTime() {
        return pickUpDateTime;
    }

    public ZonedDateTime getDropOffDateTime() {
        return dropOffDateTime;
    }

    public double getDistanceTrip() {
        return distanceTrip;
    }
}
