package Backend.Project.TaxiCompany.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.swing.*;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

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
    public Invoice setCreatedDate(ZonedDateTime dateTime)
    {
        createdDate=dateTime;
        return this;
    }
    public Invoice setBooking(Booking booking)
    {
        this.booking=booking;
        return  this;
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
    public Booking getBooking(){return booking;}
}
