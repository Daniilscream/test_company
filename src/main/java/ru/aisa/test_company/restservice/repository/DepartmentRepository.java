package ru.aisa.test_company.restservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aisa.test_company.restservice.model.Department;

public interface DepartmentRepository extends CrudRepository<Department,Long> {
}
