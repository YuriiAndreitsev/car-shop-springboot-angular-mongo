package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.security.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/cars")
@Slf4j
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCars() {
//        log.warn("Fetchin");
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.addCar(car));
    }

    @GetMapping("search/{term}")
    public ResponseEntity<List<Car>> searchCarsByTerm (@PathVariable(name = "term") String term){
        return ResponseEntity.ok(carService.searchCarsContainingBrandOrModel(term));
    }
}
