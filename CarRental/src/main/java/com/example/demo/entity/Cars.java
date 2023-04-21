package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_generator")
    @SequenceGenerator(name = "car_generator", sequenceName = "car_seq", allocationSize = 1)
    private int carId;

    private String make;

    private String model;

    private String year;

    private String currency;

    private int dailyRate;

    @OneToMany(mappedBy = "car")
    private Set<Bookings> bookings;

    public Cars(String make, String model, String year, String currency, int dailyRate, Set<Bookings> bookings) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.currency = currency;
        this.dailyRate = dailyRate;
        this.bookings = bookings;
    }

    public Cars() {

    }
}
