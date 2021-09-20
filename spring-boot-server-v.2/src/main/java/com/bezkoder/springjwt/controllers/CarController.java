package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.repository.SearchCriteria;
import com.bezkoder.springjwt.services.CarService;
import com.bezkoder.springjwt.utils.ImageUrlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
//@CrossOrigin(origins = {"*"})
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cars")
@Slf4j
public class CarController {
    @Autowired
    CarService carService;
    @Autowired
    ImageUrlBuilder imageBuilder;

//    @PostMapping("/test")
//    public String test(@RequestBody Car car) {
//        return imageBuilder.convertUploadedCarParamsToImageUrl(car);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/all-by-brand/{brand}")
    public ResponseEntity<List<Car>> getAllCarsByBrand(@PathVariable(name = "brand") String brand) {
        return ResponseEntity.ok(carService.getAllCarsByBrand(brand));
    }

    @GetMapping("/all-paged-filter-brand")
    public ResponseEntity<List<Car>> getAllCarsPagedWithFilterModel(SearchCriteria criteria) {
        return ResponseEntity.ok(carService.findCarsByModelPaged(criteria));
    }

    @GetMapping("/all-paged-model")
    public ResponseEntity<List<Car>> getAllCarsPagedWithModel(SearchCriteria criteria) {
        return ResponseEntity.ok(carService.getAllCarsPagedWithModel(criteria).getContent());
    }

    @GetMapping("/brands")
    public ResponseEntity<Set<String>> getBrands() {
        return ResponseEntity.ok(carService.getBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        car.setUrlImageList(new HashSet<String>());
        car.getUrlImageList().add(imageBuilder.convertUploadedCarParamsToImageUrl(car));
        log.warn("new car is : {}", car);
        return ResponseEntity.ok(carService.addCar(car));
    }

    @GetMapping("search/{term}")
    public ResponseEntity<List<Car>> searchCarsByTerm(@PathVariable(name = "term") String term) {
        return ResponseEntity.ok(carService.searchCarsContainingBrandOrModel(term));
    }
}
