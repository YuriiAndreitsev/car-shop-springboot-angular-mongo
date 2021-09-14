package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    Page<Car> findCarsByBrand_BrandNameIgnoreCase(String model, Pageable pageable);

    //    List<Car> findCarsByBrandIsContainingOrModelContaining(String model);
//    List<Car> findCarsByBrand_BrandNameContainingOrModelContaining(String brand_brandName, String model);
    List<Car> findCarsByBrand_BrandNameContainingIgnoreCaseOrModelContainingIgnoreCase(String brand_brandName, String model);

}
