package ru.aisa.test_company.restservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.aisa.test_company.restservice.model.DepartmentEntity;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity,Long> {

    @Query("from DepartmentEntity d where " +
            "nameDepartment like concat('%', :name, '%')")
    List<DepartmentEntity> findByName(@Param("name") String name);

    DepartmentEntity findByNameDepartment(String name);
}
