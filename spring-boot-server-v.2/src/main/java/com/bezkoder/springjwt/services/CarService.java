package com.bezkoder.springjwt.services;

import com.bezkoder.springjwt.models.Brand;
import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.models.CarPagedResponse;
import com.bezkoder.springjwt.repository.CarCriteriaRepository;
import com.bezkoder.springjwt.repository.CarRepository;
import com.bezkoder.springjwt.repository.SearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService {
    @Autowired
    CarRepository carRepository;
    @Autowired
    CarCriteriaRepository carCriteriaRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Page<Car> getAllCarsPagedWithModel(SearchCriteria searchCriteria) {
        return carCriteriaRepository.filterData(searchCriteria);
    }


    public Car getCarById(String id) {
        return carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Unable to find car by id. Your id to search : " + id));
    }

    public Car addCar(Car car) {
        return carRepository.insert(car);
    }

    public List<Car> findCarsByModelPaged(SearchCriteria search) {
        Integer page = Optional.ofNullable(search.getOffset()).orElse(0);
        Integer size = Optional.ofNullable(search.getLimit()).orElse(6);
        Pageable paging = PageRequest.of(page, size);
        Page<Car> carsPage = carRepository.findCarsByBrand_BrandNameIgnoreCase(search.getBrand(), paging);
        return carsPage.getContent();
    }

    public CarPagedResponse findAllCarsPaged(Integer pages) {
        int page = Optional.ofNullable(pages).orElse(0);
//        int size = Optional.of(request.getPageSize()).orElse(6);
        Pageable paging = PageRequest.of(page, 6);
        Page<Car> carsPage = carRepository.findAll(paging);
        return new CarPagedResponse(carsPage.getContent(), page, (int) carsPage.getTotalElements(), 6);
    }

    public List<Car> searchCarsContainingBrandOrModel(String term) {
        return carRepository.findCarsByBrand_BrandNameContainingIgnoreCaseOrModelContainingIgnoreCase(term, term);
    }

    public Set<String> getBrands() {
        Set<String> brands = carRepository.findAll().stream().map(Car::getBrand).map(Brand::getBrandName).collect(Collectors.toSet());
        log.warn("Brands : {}",brands);

        return brands;
//        return brandRepository.getDistinctByBrandName().stream().map(Brand::getBrandName).collect(Collectors.toList());
    }

    public List<Car> getAllCarsByBrand(String brand) {
        return carRepository.findAllByBrandBrandName(brand);
    }
}
