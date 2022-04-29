package Backend.Project.TaxiCompany.Entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private ZonedDateTime createdDate;
    @Column
    private float revenue;
    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;
    public Invoice(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
    //setter

    public Invoice setRevenue(float revenue) {
        this.revenue = revenue;
        return this;
    }
    public Invoice setBooking(Booking booking)
    {
        this.booking=booking;
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
