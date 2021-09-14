package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarPagedResponse {
    private List<Car> pageContent;
    private int pageNumber;
    private int totalElements;
    private int pageSize;
}
