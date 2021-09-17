package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {
    Set<Brand> getDistinctByBrandName();
}
