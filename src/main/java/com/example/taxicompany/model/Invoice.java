package com.example.taxicompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private Booking booking;
    public Invoice(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Invoice() {

    }

    //setter
    public Invoice setRevenue(float revenue) {
        this.revenue = revenue;
        return this;
    }

    public Booking getBooking() {
        return booking;
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
