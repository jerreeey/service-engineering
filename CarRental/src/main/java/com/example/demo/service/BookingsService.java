package com.example.demo.service;

import com.example.demo.DTO.BookingsDTO;
import com.example.demo.entity.Cars;
import com.example.demo.entity.Users;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.CarNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.BookingsRepository;
import com.example.demo.repository.CarsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.entity.Bookings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingsService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CarsRepository carsRepository;

    public List<BookingsDTO> findBookingsByUserId(int userId) {
        List<BookingsDTO> bookingsDTOS = new ArrayList<>();
        Users user = usersRepository.findUserByUserId(userId);
        if (user != null) {
            List<Bookings> bookings = bookingsRepository.findByUserId(user.getUserId());
            bookings.forEach(o -> bookingsDTOS.add(convertBookingToBookingDTO(o)));
        }
        return bookingsDTOS.stream().sorted( (a,b) -> Boolean.compare(a.isReturned(), b.isReturned())).collect(Collectors.toList());
    }

    public BookingsDTO submitNewBooking(BookingsDTO bookingsDTO) throws UserNotFoundException, CarNotFoundException, ParseException {
        Bookings booking = new Bookings();
        Users user = usersRepository.findUserByUserId(bookingsDTO.getUserId());
        if (user != null) {
            Optional<Cars> car = carsRepository.findById(bookingsDTO.getCarId());
            if (car.isPresent()) {
                car.get().setCarId(bookingsDTO.getCarId());
                booking.setCar(car.get());
                booking.setUser(user);
                Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingsDTO.getPickupDate() + " " + bookingsDTO.getPickupHour());
                Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingsDTO.getReturnDate() + " " + bookingsDTO.getReturnHour());
                booking.setStartDate(pickupDate);
                booking.setEndDate(returnDate);
                booking.setReturned(false);
                Bookings savedBooking = bookingsRepository.save(booking);
                return convertBookingToBookingDTO(savedBooking);
            }
            throw new CarNotFoundException("Car with Id " + bookingsDTO.getCarId() + " not found");
        }
        throw new UserNotFoundException("User with Id " + bookingsDTO.getUserId() + " not found");
    }

    private BookingsDTO convertBookingToBookingDTO(Bookings booking) {
        BookingsDTO bookingsDTO = new BookingsDTO();
        bookingsDTO.setBookingId(booking.getBookingId());
        bookingsDTO.setCarId(booking.getCar().getCarId());
        bookingsDTO.setUserId(booking.getUser().getUserId());
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_FORMAT);
        String startDate = dateFormatter.format(booking.getStartDate());
        String startTime = timeFormatter.format(booking.getStartDate());
        String endDate = dateFormatter.format(booking.getEndDate());
        String endTime = timeFormatter.format(booking.getEndDate());
        bookingsDTO.setPickupDate(startDate);
        bookingsDTO.setPickupHour(startTime);
        bookingsDTO.setReturnDate(endDate);
        bookingsDTO.setReturnHour(endTime);
        bookingsDTO.setReturned(booking.getReturned());
        bookingsDTO.setMake(booking.getCar().getMake());
        bookingsDTO.setModel(booking.getCar().getModel());
        bookingsDTO.setYear(booking.getCar().getYear());
        return bookingsDTO;
    }

    public BookingsDTO returnCar(int bookingId) throws BookingNotFoundException {
        Optional<Bookings> booking = bookingsRepository.findById(bookingId);
        if (booking.isPresent()) {
            booking.get().setReturned(true);
            Bookings savedBooking = bookingsRepository.save(booking.get());
            return convertBookingToBookingDTO(savedBooking);
        }
        throw new BookingNotFoundException("Booking with Id " + bookingId + " not found");
    }
}
