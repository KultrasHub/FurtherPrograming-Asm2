package Backend.Project.TaxiCompany.Model;

import javax.persistence.*;
import java.time.ZonedDateTime;
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

    public Customer(long id) {
        this.id = id;
    }

    public Customer() {
        this.createdDate=ZonedDateTime.now();
    }

    //builder
    public  Customer setId(long id)
    {
        this.id=id;
        return this;
    }
    public Customer setName(String name)
    {
        this.name=name;
        return this;
    }
    public Customer setAddress(String address)
    {
        this.address = address;
        return this;
    }
    public Customer setPhone(String phone)
    {
        this.phone=phone;
        return this;
    }
    public Customer setCreatedDate(ZonedDateTime dateTime)
    {
        createdDate=dateTime;
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
