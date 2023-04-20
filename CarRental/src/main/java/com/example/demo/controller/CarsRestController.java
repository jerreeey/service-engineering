package com.example.demo.controller;

import com.example.demo.DTO.CarsDTO;
import com.example.demo.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarsRestController {

    @Autowired
    CarsService carsService;

    @GetMapping
    public List<CarsDTO> findUser(@RequestParam String pickupdate, @RequestParam String pickuphour, @RequestParam String returndate, @RequestParam String returnhour, @RequestParam String currency) {
        //return all cats with selected currency where no booking is between pickup and return + hours
        return carsService.findCars(pickupdate, pickuphour, returndate, returnhour, currency);
    }
}
