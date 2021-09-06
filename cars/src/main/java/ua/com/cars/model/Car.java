package ua.com.cars.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
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
