package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.repository.CarRepository;
import com.bezkoder.springjwt.search.PagedResponse;
import com.bezkoder.springjwt.search.SearchRequest;
import com.bezkoder.springjwt.search.SearchRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public PagedResponse<Car> getAllCarsPaged(SearchRequest request) {
        final Page<Car> response = carRepository.findAll(SearchRequestUtil.toPageRequest(request));
        if (response.isEmpty()) {
            return new PagedResponse<Car>(Collections.emptyList(), 0, response.getTotalElements());
        }
        final List<Car> cars = response.getContent();
        return new PagedResponse<>(cars, cars.size(), response.getTotalElements());
    }

    public Car getCarById(String id) {
        return carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Unable to find car by id. Your id to search : " + id));
    }

    public Car addCar(Car car) {
        return carRepository.insert(car);
    }

    public List<Car> searchCarsContainingBrandOrModel(String term) {
        List<Car> cars = carRepository.findCarsByModelIsContainingOrBrand_BrandNameIsContaining(term, term);
        if (cars.isEmpty()){
            String firstLetterToUpperCase = "";
            if (term.length()==1){
                firstLetterToUpperCase = term.toUpperCase();
            }else {
                firstLetterToUpperCase = term;
            }
            term.compareToIgnoreCase(term);
            String termWithFirstLetterUpperCase = firstLetterToUpperCase.substring(0, 1).toUpperCase() + firstLetterToUpperCase.substring(1);
            cars = carRepository.findCarsByModelIsContainingOrBrand_BrandNameIsContaining(termWithFirstLetterUpperCase, termWithFirstLetterUpperCase);
        }
        if (cars.isEmpty()){
            cars = carRepository.findCarsByModelIsContainingOrBrand_BrandNameIsContaining(term.toUpperCase(), term.toUpperCase());
        }
        return cars;
//        return carRepository.findCarsByModelIsContainingOrBrand_BrandNameIsContaining(term, term);
//        return carRepository.findCarsByBrand_BrandNameContainingOrModelContaining(new Brand(term, null,null),term);
    }
}
