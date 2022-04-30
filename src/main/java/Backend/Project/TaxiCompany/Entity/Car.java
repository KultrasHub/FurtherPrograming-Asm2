package Backend.Project.TaxiCompany.Entity;

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

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Car(ZonedDateTime createdDate, String licensePlate) {
        this.createdDate = createdDate;
        this.licensePlate = licensePlate;
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

    public void setId(long id) {
        this.id = id;
    }
}
