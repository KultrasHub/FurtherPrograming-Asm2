package Backend.Project.TaxiCompany.Entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private ZonedDateTime createdDate;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch =
            FetchType.EAGER)
    private List<Booking> booking;

    public Customer(ZonedDateTime createdDate, String name) {
        this.createdDate = createdDate;
        this.name = name;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    public Customer() {

    }

    //builder
    public Customer setAddress(String address)
    {
        this.address = address;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer setPhone(String phone)
    {
        this.phone=phone;
        return this;
    }
    //getter

    public long getId() {
        return id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
