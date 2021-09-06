package ua.com.cars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.com.cars.model.Role;

//no need for this testing example
@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    Role findByName(String userName);
}
