package ru.aisa.test_company.restservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.aisa.test_company.restservice.model.Employee;
import ru.aisa.test_company.restservice.model.WorkingDay;

import java.util.Date;
import java.util.Optional;

public interface WorkingDayRepository extends CrudRepository<WorkingDay,Long> {

    @Query("Select COUNT(*) FROM WorkingDay w INNER JOIN w.workingEmployees e WHERE e = :employee")
    Integer findByWorkingDays(@Param("employee") Employee employee);

    @Query("Select MAX(w.date) FROM WorkingDay w INNER JOIN w.employeesOnVacation e WHERE e = :employee")
    Date findByLastVacationDay(@Param("employee") Employee employee);

    WorkingDay findByDate(Date date);
}
