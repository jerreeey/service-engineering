package com.example.demo.controller;


import com.example.demo.DTO.BookingsDTO;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.CarNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingsRestController {

    @Autowired
    BookingsService bookingsService;

    @PostMapping
    public ResponseEntity submitNewBooking(@RequestBody BookingsDTO bookingsDTO) throws ParseException {
        try {
            return ResponseEntity.ok(bookingsService.submitNewBooking(bookingsDTO));
        } catch (UserNotFoundException | CarNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<BookingsDTO> findUser(@PathVariable("userId") int userId) {
        //return user with id
        return bookingsService.findBookingsByUserId(userId);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity returnCar(@PathVariable("bookingId") int bookingId) {
        try {
            return ResponseEntity.ok(bookingsService.returnCar(bookingId));
        } catch (BookingNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
