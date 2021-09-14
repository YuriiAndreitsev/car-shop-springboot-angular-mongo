package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.models.CarPagedResponse;
import com.bezkoder.springjwt.models.PagedRequest;
import com.bezkoder.springjwt.repository.SearchCriteria;
import com.bezkoder.springjwt.security.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/all-paged-filter-brand")
    public ResponseEntity<List<Car>> getAllCarsPagedWithFilterModel(SearchCriteria criteria) {
        return ResponseEntity.ok(carService.findCarsByModelPaged(criteria));
    }

    @GetMapping("/all-paged-model")
    public ResponseEntity<List<Car>> getAllCarsPagedWithModel(SearchCriteria criteria) {
        return ResponseEntity.ok(carService.getAllCarsPagedWithModel(criteria).getContent());
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
