package ru.aisa.test_company.restservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aisa.test_company.restservice.model.EmployeeEntity;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Long> {

}
