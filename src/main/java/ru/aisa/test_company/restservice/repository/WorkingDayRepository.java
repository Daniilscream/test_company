package ru.aisa.test_company.restservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.restservice.model.WorkingDay;

import java.util.Date;

public interface WorkingDayRepository extends CrudRepository<WorkingDay,Long> {

    @Query("Select COUNT(*) FROM WorkingDay w INNER JOIN w.workingEmployeeEntities e WHERE e = :employee")
    Integer findByWorkingDays(@Param("employee") EmployeeEntity employeeEntity);

    @Query("Select MAX(w.date) FROM WorkingDay w INNER JOIN w.employeesOnVacation e WHERE e = :employee")
    Date findByLastVacationDay(@Param("employee") EmployeeEntity employeeEntity);

    WorkingDay findByDate(Date date);
}
