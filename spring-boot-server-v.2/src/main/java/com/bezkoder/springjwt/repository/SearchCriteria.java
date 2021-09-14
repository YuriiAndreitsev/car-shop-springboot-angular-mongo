package com.bezkoder.springjwt.repository;

import lombok.Data;

@Data
public class SearchCriteria {
    int offset;
    int limit;
    String model;
    String brand;
}
