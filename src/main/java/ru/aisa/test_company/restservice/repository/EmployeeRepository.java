package ru.aisa.test_company.restservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.aisa.test_company.restservice.model.Employee;

import java.util.Date;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {

//    @Query("")
//    Date findByLastVacation(Long employeeId);
}
