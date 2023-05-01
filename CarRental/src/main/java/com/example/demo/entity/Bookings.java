package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
    @SequenceGenerator(name = "booking_generator", sequenceName = "booking_seq", allocationSize = 1, initialValue = 6)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "carId", nullable = false)
    @JsonIgnore
    private Cars car;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    @JsonIgnore
    private Users user;

    private Date startDate;

    private Date endDate;

    @Column(name = "returned", columnDefinition = "BIT")
    private Boolean returned;

    public Bookings(Cars car, Users user, Date startDate, Date endDate, Boolean returned) {
        this.car = car;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = returned;
    }

    public Bookings() {

    }
}
