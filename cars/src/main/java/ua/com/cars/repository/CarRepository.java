package ua.com.cars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.com.cars.model.Car;
@Repository
public interface CarRepository extends MongoRepository<Car, String> {
}