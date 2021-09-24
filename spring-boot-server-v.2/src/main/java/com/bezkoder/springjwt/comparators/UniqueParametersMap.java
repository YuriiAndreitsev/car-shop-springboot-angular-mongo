package com.bezkoder.springjwt.comparators;

import com.bezkoder.springjwt.models.Car;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class UniqueParametersMap {
    private Map<String, Comparator<Car>> map = new HashMap<>();

    public UniqueParametersMap() {
        map.put("model", CarComparators.MODEL);
        map.put("brand", CarComparators.BRAND);
        map.put("price", CarComparators.PRICE);
        map.put("productionDate", CarComparators.PRODUCTION_DATE);
    }

    public Map<String, Comparator<Car>> getMap() {
        return map;
    }
}
