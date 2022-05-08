package Backend.Project.TaxiCompany.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    private ZonedDateTime createdDate;
    @Column
    private Float revenue;

    public Invoice() {
        this.createdDate=ZonedDateTime.now();
    }

    public Invoice(ZonedDateTime createdDate, Float revenue) {
        this.createdDate = createdDate;
        this.revenue = revenue;
    }

    public Invoice(Long id, ZonedDateTime createdDate, Float revenue) {
        this.id = id;
        this.createdDate = createdDate;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Float getRevenue() {
        return revenue;
    }

    public void setRevenue(Float revenue) {
        this.revenue = revenue;
    }
}
