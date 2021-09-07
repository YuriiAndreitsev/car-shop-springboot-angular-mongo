package ua.com.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    private String brandName;
    private String countryOrigin;
    private LocalDateTime created;
}
