package ru.aisa.test_company.restservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aisa.test_company.restservice.model.DepartmentEntity;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity,Long> {
}
