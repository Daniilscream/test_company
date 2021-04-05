package ru.aisa.test_company.restservice.dto;

import ru.aisa.test_company.restservice.model.Department;
import ru.aisa.test_company.restservice.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentDTO {

    private Long id;

    private String nameDepartment;

    private List<EmployeeDTO> employees;

    private int workingHours = 0;

    public DepartmentDTO(Department department) {
            this.id = department.getId();
            this.nameDepartment = department.getNameDepartment();
            this.employees = department.getEmployees().stream()
                    .map(employee -> new EmployeeDTO(employee))
                    .collect(Collectors.toList());
            for (Employee e : department.getEmployees()) {
                workingHours += e.getTime();
            }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }
}
