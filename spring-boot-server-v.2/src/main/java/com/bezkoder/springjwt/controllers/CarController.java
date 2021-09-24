package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Brand;
import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.payload.checkbox.CarParamsTask;
import com.bezkoder.springjwt.repository.SearchCriteria;
import com.bezkoder.springjwt.services.CarComparingService;
import com.bezkoder.springjwt.services.CarService;
import com.bezkoder.springjwt.utils.ImageUrlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@CrossOrigin(origins = {"*"})
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cars")
@Slf4j
public class CarController {
    @Autowired
    CarService carService;
    @Autowired
    ImageUrlBuilder imageBuilder;
    @Autowired
    CarComparingService comparingService;

    @PostMapping("/unique")
    public ResponseEntity<List<Car>> test(@RequestBody CarParamsTask task) {
        List<Car> carList = carService.getAllCars();
        comparingService.resolveCarComparation(carList, new String[]{"Tesla", "citroen", "BWM", "Honda", "Toyota"}, comparingService.getParamsToUniquelize(task.getSubtasks()), "brand");
        return ResponseEntity.ok(carList);
    }

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
        car.setUrlImageList(new HashSet<>());
        car.getUrlImageList().add(imageBuilder.convertUploadedCarParamsToImageUrl(car));
        return ResponseEntity.ok(carService.addCar(car));
    }

    @GetMapping("search/{term}")
    public ResponseEntity<List<Car>> searchCarsByTerm(@PathVariable(name = "term") String term) {
        return ResponseEntity.ok(carService.searchCarsContainingBrandOrModel(term));
    }
}
