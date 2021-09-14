package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
public class CarCriteriaRepository {
    @Autowired
    MongoTemplate mongoTemplate;
    public Page<Car> filterData(SearchCriteria search){
        List<Car> list;
        Integer offset = Optional.ofNullable(search.getOffset()).orElse(0);
        Integer limit = Optional.ofNullable(search.getLimit()).orElse(6);
        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit);
        Query query = new Query();
        /** your filter condition */
         if (!StringUtils.isEmpty(search.getBrand())) {
              query.addCriteria(Criteria.where("brandName").is(search.getBrand()));
           }
        query.with(pageable);
        list = mongoTemplate.find(query, Car.class);
        return PageableExecutionUtils.getPage(list, pageable,
                ()-> mongoTemplate.count(query, Car.class));
    }
}
