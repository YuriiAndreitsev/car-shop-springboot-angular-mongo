package com.bezkoder.springjwt.comparators;


import com.bezkoder.springjwt.models.Car;

import java.util.Comparator;

public enum CarComparators implements Comparator<Car> {
    MODEL(Comparator.comparing(Car::getModel)),
    BRAND(Comparator.comparing(car -> car.getBrand().getBrandName())),
    PRICE(Comparator.comparing(Car::getPrice)),
    PRODUCTION_DATE(Comparator.comparing(Car::getCreated));

    Comparator<Car> comparator;

    CarComparators(Comparator<Car> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(Car o1, Car o2) {
        int result = comparator.compare(o1, o2);
        if (result != 0) {
            return result;
        }
        return 0;
    }
}
