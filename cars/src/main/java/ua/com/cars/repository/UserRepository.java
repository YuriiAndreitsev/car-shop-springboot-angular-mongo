package ua.com.cars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.com.cars.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository <User, String> {
    Optional<User> findByUsername(String userName);
}
