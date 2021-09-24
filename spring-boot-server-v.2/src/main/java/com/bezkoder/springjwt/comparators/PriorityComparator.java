package com.bezkoder.springjwt.comparators;

import com.bezkoder.springjwt.models.Car;

import java.util.Comparator;
import java.util.List;

public class PriorityComparator implements Comparator<Car> {
    List<Comparator<Car>> compList;

    public PriorityComparator() {

    }

    public PriorityComparator(List<Comparator<Car>> compList) {
        this.compList = compList;
        primary = compList.get(0);
        secondary = compList.get(1);
    }

    Comparator<Car> primary;
    Comparator<Car> secondary;

    @Override
    public int compare(Car o1, Car o2) {

        int result;
        if ((result = primary.compare(o1, o2)) == 0) {
            return secondary.compare(o1, o2);
        } else {
            return result;
        }

    }
}
