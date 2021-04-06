package ru.aisa.test_company.restservice.service;

import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.swagger.model.Employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public static List<Employee> convertEmployeeEntity(List<EmployeeEntity> employees){
        List<Employee> employeeList = new ArrayList<>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        for (EmployeeEntity e :employees) {
            Employee employee = new Employee();
            employee.setDateOfReceipt(formatDate.format(e.getDateOfReceipt()));
            employee.setFullName(e.getFullName());
            employee.setId(e.getId());
            employee.setPosition(e.getPosition());
            employeeList.add(employee);
        }
        return employeeList;
    }
}
