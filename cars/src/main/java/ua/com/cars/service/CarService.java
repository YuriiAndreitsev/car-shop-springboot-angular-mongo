package ua.com.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.cars.model.Car;
import ua.com.cars.repository.CarRepository;

import java.util.List;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public Car addCar(Car car){
        return carRepository.insert(car);
    }
}
