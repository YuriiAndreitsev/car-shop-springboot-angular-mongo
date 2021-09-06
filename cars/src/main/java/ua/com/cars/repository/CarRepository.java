package ua.com.cars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.com.cars.model.Car;

public interface CarRepository extends MongoRepository<Car, String> {
}
