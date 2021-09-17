package com.bezkoder.springjwt.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "cars")
public class Car {
    @Id
    private String id;
    private String model;
    private Brand brand;
    private String number;
    private LocalDateTime created;
    private int price;
    private Set<String> urlImageList;
}
