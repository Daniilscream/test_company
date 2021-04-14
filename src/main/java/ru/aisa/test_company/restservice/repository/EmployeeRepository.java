package ru.aisa.test_company.restservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.restservice.model.EmployeeEntity;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Long> {

    @Query("from EmployeeEntity e where " +
            "fullName like concat('%', :name, '%')")
    List<EmployeeEntity> findByName(@Param("name") String name);

}
