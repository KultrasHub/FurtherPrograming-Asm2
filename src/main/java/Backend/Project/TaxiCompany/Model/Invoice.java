package Backend.Project.TaxiCompany.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    private ZonedDateTime createdDate;
    @Column
    private float revenue;

    public void setId(long id) {
        this.id = id;
    }

    public Invoice() {
        this.createdDate=ZonedDateTime.now();
    }

    //setter
    public Invoice setRevenue(float revenue) {
        this.revenue = revenue;
        return this;
    }


    //getter
    public long getId() {
        return id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public float getRevenue() {
        return revenue;
    }
}
