package ua.com.cars.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Brand {
    private String brandName;
    private String countryOrigin;
    private LocalDateTime created;
}
