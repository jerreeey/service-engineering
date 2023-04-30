package com.example.demo.service;

import com.example.demo.DTO.BookingsDTO;
import com.example.demo.DTO.CarsDTO;
import com.example.demo.entity.Bookings;
import com.example.demo.entity.Cars;
import com.example.demo.repository.CarsRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarsService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    CarsRepository carsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<CarsDTO> findCars(String pickupdate, String pickuphour, String returndate, String returnhour, String currency) throws ParseException {
        List<CarsDTO> carsDTOS = new ArrayList<>();
        List<Cars> cars = carsRepository.findAll();
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(pickupdate + " " + pickuphour);
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(returndate + " " + returnhour);
        List<Cars> availableCars = new ArrayList<>();
        for (Cars car: cars) {
            Set<Bookings> bookings = car.getBookings();
            boolean carAvailable = bookings.stream().allMatch(b -> isNotInRange(b.getStartDate(), pickupDate, returnDate) && isNotInRange(b.getEndDate(), pickupDate, returnDate));
            if(carAvailable) {
                availableCars.add(car);
            }
        }
        //todo put available cars from Dollar in currency converter and return with selected currency
        availableCars.forEach(car -> carsDTOS.add(convertCarToCarDTO(car)));
        return carsDTOS;
    }

    private boolean isNotInRange(Date date, Date startDate, Date endDate) {
        return date.before(startDate) || date.after(endDate);
    }

    private CarsDTO convertCarToCarDTO(Cars cars) {
        CarsDTO carsDTO = new CarsDTO();
        carsDTO.setCarId(cars.getCarId());
        carsDTO.setMake(cars.getMake());
        carsDTO.setModel(cars.getModel());
        carsDTO.setYear(cars.getYear());
        carsDTO.setCurrency(cars.getCurrency());
        carsDTO.setDailyRate(cars.getDailyRate());
        return carsDTO;
    }

}
