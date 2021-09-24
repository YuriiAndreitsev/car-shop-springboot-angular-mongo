package com.bezkoder.springjwt.services;

import com.bezkoder.springjwt.comparators.CarComparators;
import com.bezkoder.springjwt.comparators.ComparatorsChaining;
import com.bezkoder.springjwt.comparators.PriorityComparator;
import com.bezkoder.springjwt.comparators.UniqueParametersMap;
import com.bezkoder.springjwt.models.Car;
import com.bezkoder.springjwt.payload.checkbox.CarParamsTask;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarComparingService {

    public String[] getParamsToUniquelize(CarParamsTask[] tasks){
        List <String> paramsToUniquelize = new ArrayList<>();
        for (CarParamsTask t : tasks){
           if ( t.isCompleted()){
               paramsToUniquelize.add(t.getName().toLowerCase());
           }
        }
        return paramsToUniquelize.toArray(new String[0]);
    }
    public void resolveCarComparation(List<Car> carCollection, String[] brandToUniquelize,
                                      String[] uniquenessParams, String priority) {
        Collection<String> brandToUniquelizeCollection;
        Collection<Car> toBeRemovedFromInitialCarCollection = new ArrayList<>();
        List<Comparator<Car>> priorityComparatorList = new ArrayList<>();

        if (brandToUniquelize != null && brandToUniquelize.length > 0) {
            brandToUniquelizeCollection = new ArrayList<>(Arrays.asList(brandToUniquelize));
        } else {
            throw new IllegalArgumentException("Brand to uniquelize if undefined");
        }

        if (priority.equals("brand")) {
            priorityComparatorList.add(CarComparators.BRAND);
            priorityComparatorList.add(CarComparators.PRICE);
        } else {
            priorityComparatorList.add(CarComparators.PRICE);
            priorityComparatorList.add(CarComparators.BRAND);
        }

        PriorityComparator priorityComparator = new PriorityComparator(priorityComparatorList);
        Collection<Comparator<Car>> carUniquenessComparators = new ArrayList<>();

        if (uniquenessParams != null && uniquenessParams.length > 0) {
            for (String uniquenessParam : uniquenessParams) {
                carUniquenessComparators.add(new UniqueParametersMap().getMap().get(uniquenessParam));
            }
        }


        ComparatorsChaining chainUniqueness = new ComparatorsChaining();
        Comparator<Car> finalUniquenessComparator = chainUniqueness.createChainComparator(carUniquenessComparators);

        Collection<Car> uniqueCarCollection = new TreeSet<>(finalUniquenessComparator);

        if (!brandToUniquelizeCollection.isEmpty()) {
            brandToUniquelizeCollection.forEach(brand -> {
                carCollection.stream().filter(car -> car.getBrand().getBrandName().equals(brand)).forEach(car -> {
                    uniqueCarCollection.add(car);
                    toBeRemovedFromInitialCarCollection.add(car);
                });
            });

            carCollection.removeAll(toBeRemovedFromInitialCarCollection);
            carCollection.addAll(uniqueCarCollection);
        } else {
            throw new IllegalArgumentException("Brand to Uniquelize Collection is Empty");
        }
        carCollection.sort(priorityComparator);
    }
}
