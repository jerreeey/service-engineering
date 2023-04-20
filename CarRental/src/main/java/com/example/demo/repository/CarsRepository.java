package com.example.demo.repository;

import com.example.demo.entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Integer> {

    @Query(value = "SELECT c.make, c.model, c.year, c.currency, c.daily_rate, b.start_date, b.end_date, b.returned, c.car_id as id, b.car_id as car_id FROM Bookings b LEFT JOIN Cars c ON c.car_id = b.car_id",
            nativeQuery = true)
    List<Cars> getAllCars();

    //todo filter?
}
