package com.example.demo.controller;


import com.example.demo.DTO.BookingsDTO;
import com.example.demo.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingsRestController {

    @Autowired
    BookingsService bookingsService;

    @GetMapping("/user/{userId}")
    public List<BookingsDTO> findUser(@PathVariable("userId") int userId) {
        //return user with id
        return bookingsService.findBookingsByUserId(userId);
    }

    //todo
    @PutMapping("/{bookingId}")
    public String returnCar(@PathVariable("bookingId") int bookingId) {
        //return Car and return updated status
        return "TestPUT";
    }

    //todo
    @PostMapping
    public String submitNewBooking(@RequestBody BookingsDTO bookingsDTO) {
        //submit new booking and return success
        return "TestPOST";
    }
}
