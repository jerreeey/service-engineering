package com.example.demo.DTO;

import com.example.demo.entity.Bookings;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CarsDTO {

    private int carId;

    private String make;

    private String model;

    private String year;

    private String currency;

    private int dailyRate;

    private Set<Bookings> bookings;
}
