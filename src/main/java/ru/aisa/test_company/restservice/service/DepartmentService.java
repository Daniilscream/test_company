package ru.aisa.test_company.restservice.service;

import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.swagger.model.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {



    public static List<Department> convertDepartment(List<DepartmentEntity> departmentEntities){
        List<Department> departments = new ArrayList<>();
        for (DepartmentEntity de : departmentEntities) {
            Department department = new Department();
            department.setEmployees(EmployeeService.convertEmployeeEntity(de.getEmployees()));
            department.setId(de.getId());
            department.setNameDepartment(de.getNameDepartment());
            departments.add(department);
        }
        return departments;
    }
}
