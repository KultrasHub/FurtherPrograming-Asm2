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


    public Driver() {
        this.createdDate=ZonedDateTime.now();
    }

    public Driver setName(String name) {
        this.name = name;
        return  this;
    }
    public Driver setCreatedDate(ZonedDateTime dateTime)
    {
        createdDate=dateTime;
        return this;
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
