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
    @Column
    private String licensePlate;

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
