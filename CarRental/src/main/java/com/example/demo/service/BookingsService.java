package com.example.demo.service;

import com.example.demo.DTO.BookingsDTO;
import com.example.demo.entity.Users;
import com.example.demo.repository.BookingsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.entity.Bookings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingsService {
    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UsersRepository usersRepository;

    public List<BookingsDTO> findBookingsByUserId(int userId) {
        List<BookingsDTO> bookingsDTOS = new ArrayList<>();
        Users users = usersRepository.findUserByUserId(userId);
        if (users != null) {
            List<Bookings> bookings = bookingsRepository.findByUserId(users.getUserId());
            bookings.forEach(o -> bookingsDTOS.add(convertToDTO(o)));
        }
        return bookingsDTOS;
    }

    private BookingsDTO convertToDTO(Bookings bookings) {
        BookingsDTO bookingsDTO = new BookingsDTO();
        //todo convert
        return bookingsDTO;
    }
}
