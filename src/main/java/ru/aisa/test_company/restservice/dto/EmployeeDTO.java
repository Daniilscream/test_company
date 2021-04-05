package ru.aisa.test_company.restservice.dto;

import ru.aisa.test_company.restservice.model.Employee;

import java.util.Date;

public class EmployeeDTO {

    private Long id;

    private Date dateOfReceipt;

    private String fullName;

    private String position;

    private long departmentId;

    private Integer time;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.dateOfReceipt = employee.getDateOfReceipt();
        this.fullName = employee.getFullName();
        this.position = employee.getPosition();
        this.departmentId = employee.getDepartment().getId();
        this.time = employee.getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
