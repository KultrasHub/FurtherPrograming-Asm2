package Backend.Project.TaxiCompany.Entity;

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

    public Driver(ZonedDateTime createdDate, String name) {
        this.createdDate = createdDate;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }
}
