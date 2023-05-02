package com.example.demo.controller;

import com.example.demo.DTO.CarsDTO;
import com.example.demo.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarsRestController {

    @Autowired
    CarsService carsService;

    @GetMapping
    public ResponseEntity<List<CarsDTO>> findCars(@RequestParam String pickupdate, @RequestParam String pickuphour, @RequestParam String returndate, @RequestParam String returnhour, @RequestParam String currency) throws ParseException {
        return ResponseEntity.ok(carsService.findCars(pickupdate, pickuphour, returndate, returnhour, currency));
    }
}
