package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Brand {
    private String brandName;
    private String countryOrigin;
    private LocalDateTime created;
}
