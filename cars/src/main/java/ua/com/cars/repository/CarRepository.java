package ua.com.cars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.com.cars.model.Brand;
import ua.com.cars.model.Car;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findCarsByBrandIsContainingOrModelContaining(String model);
    List<Car> findCarsByBrand_BrandNameContainingOrModelContaining(String brand_brandName, String model);
}
