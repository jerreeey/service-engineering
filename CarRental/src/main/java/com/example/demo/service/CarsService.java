package com.example.demo.service;

import com.example.demo.DTO.CarsDTO;
import com.example.demo.entity.Cars;
import com.example.demo.repository.CarsRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarsService {
    @Autowired
    CarsRepository carsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<CarsDTO> findCars(String pickupdate, String pickuphour, String returndate, String returnhour, String currency) {
        List<CarsDTO> carsDTOS = new ArrayList<>();
        List<Cars> cars = carsRepository.getAllCars();
        cars.forEach(car -> carsDTOS.add(convertCarToCarDTO(car)));
        System.out.println(carsDTOS);
        return carsDTOS;
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
