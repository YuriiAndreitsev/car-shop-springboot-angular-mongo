package com.bezkoder.springjwt.utils;

import com.bezkoder.springjwt.models.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageUrlBuilder {
    StringBuilder sb;
    public String convertUploadedCarParamsToImageUrl(Car car){
        log.warn("CAR : {}", car);
        sb = new StringBuilder();
        String slash = "/";
        String baseUrl = "../../assets/images/car-images/";
        String model = prepareString(car.getModel());
        String brand = prepareString(car.getBrand().getBrandName());

        sb.append(baseUrl)
                .append(brand)
                .append(slash)
                .append(model)
                .append(slash)
                .append(createFileName(brand,model));
        return sb.toString();
    }

    private String prepareString (String s){
        return s.toLowerCase().trim().replaceAll("  ", " ").replaceAll(" ", "-").replaceAll(System.lineSeparator(), "");
    }

    private String createFileName(String brand,String model){
        String dash = "-";
        return brand + dash + model + dash + 1+".jpg";
    }
}
