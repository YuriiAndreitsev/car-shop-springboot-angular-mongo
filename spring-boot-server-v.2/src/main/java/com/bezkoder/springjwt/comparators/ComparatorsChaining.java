package com.bezkoder.springjwt.comparators;

import com.bezkoder.springjwt.models.Car;

import java.util.*;

public class ComparatorsChaining {
    private List<Comparator<Car>> listComparators;

    public ComparatorsChaining() {

    }

    public ComparatorsChaining(Comparator<Car>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }
//
//    public Collection<Comparator<Car>> createUniquenessComparatorCollection(Map<Comparator<Car>, Boolean> map) {
//        Collection<Comparator<Car>> result = new HashSet<>();
//        map.forEach((k, v) -> {
//            if (v) {
//                result.add(k);
//            }
//        });
//        result.remove(null);
//        return result;
//    }
//
//    public <T extends Comparable<? super T>> Comparator<Car> enableComparator(boolean enable,
//                                                                              Function<? super Car, ? extends String> function) {
//        if (enable) {
//            return Comparator.comparing(function);
//        }
//        return null;
//    }

    public Comparator<Car> createChainComparator(Collection<Comparator<Car>> comparators) {
        return comparators.stream().reduce((o1, o2) -> 0, Comparator::thenComparing);
    }
}
