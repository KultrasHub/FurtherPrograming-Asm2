package Backend.Project.TaxiCompany.Model;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private ZonedDateTime createdDate;
    @Column
    private String name;
    @Column
    private String licenseNumber;
    @Column
    private String phoneNumber;
    @Column
    private float rating;
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Driver setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;

    }

    public Driver setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public float getRating() {
        return rating;
    }

    public Driver setRating(float rating) {
        this.rating = rating;
        return this;
    }

    public Driver(Long id) {
        this.id = id;
    }

    public Driver(ZonedDateTime createdDate, String name) {
        this.createdDate = createdDate;
        this.name = name;
    }

    public Driver(Long id, ZonedDateTime createdDate, String name) {
        this.id = id;
        this.createdDate = createdDate;
        this.name = name;
    }

    public Driver() {
        this.createdDate=ZonedDateTime.now();
    }

    public long getId() {
        return id;
    }

    public Driver setId(long id) {
        this.id = id;
        return  this;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Driver setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public Driver setName(String name) {
        this.name = name;
        return this;
    }
}
