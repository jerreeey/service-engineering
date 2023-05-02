package com.example.demo.repository;

import com.example.demo.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

    @Query(value = "SELECT * FROM Bookings b WHERE b.user_id = ?1",
            nativeQuery = true)
    List<Bookings> findByUserId(@Param("userId") int userId);
}
