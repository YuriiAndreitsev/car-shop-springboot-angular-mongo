package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.search.SearchRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findCarsByBrand_BrandNameContainingOrModelContaining(String brand_brandName, String model);
    List<Car> findCarsByModelIsContainingOrBrand_BrandNameIsContaining(String brand_brandName, String model);
}
