package ru.aisa.test_company.restservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aisa.test_company.restservice.model.User;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String name);
}
