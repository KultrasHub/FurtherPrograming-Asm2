package Backend.Project.TaxiCompany.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.swing.*;
import java.time.ZonedDateTime;
import java.util.Objects;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    public void setId(long id) {
        this.id = id;
    }

    public Invoice() {
        this.createdDate=ZonedDateTime.now();
    }

    public Invoice(ZonedDateTime createdDate, Float revenue) {
        this.createdDate = createdDate;
        this.revenue = revenue;
    }
    public Invoice setBooking(Booking booking)
    {
        this.booking=booking;
        return  this;
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

    public Invoice setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public float getRevenue() {
        return revenue;
    }
    public Booking getBooking(){return booking;}

    public Invoice setRevenue(float revenue) {
        this.revenue = revenue;
        return this;
    }
}
