package Backend.Project.TaxiCompany.Model;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Entity
@Table(name = "car")
public class Car {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private ZonedDateTime createdDate;
    //additional
    @Column
    private String licensePlate;
    @Column
    private String make;
    @Column
    private String model;
    @Column
    private float rating;
    @Column
    private String color;
    @Column
    private String convertible;
    public Car(long id) {
        this.id = id;
    }

    public Car() {
        this.createdDate=ZonedDateTime.now();
    }

    //getter
    public long getId() {
        return id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMake() {
        return make;
    }

    public Car setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Car setModel(String model) {
        this.model = model;
        return this;
    }

    public float getRating() {
        return rating;
    }

    public Car setRating(float rating) {
        this.rating = rating;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Car setColor(String color) {
        this.color = color;
        return this;
    }

    public String getConvertible() {
        return convertible;
    }

    public Car setConvertible(String convertible) {
        this.convertible = convertible;
        return this;
    }

    public Car setId(long id)
    {
        this.id=id;
        return this;
    }
    public Car setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public Car setCreatedDate(ZonedDateTime dateTime)
    {
        createdDate=dateTime;
        return this;
    }
}
