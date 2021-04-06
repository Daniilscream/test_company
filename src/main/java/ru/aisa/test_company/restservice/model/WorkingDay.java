package ru.aisa.test_company.restservice.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class WorkingDay {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    private Date date;

    @ManyToMany
    private Set<EmployeeEntity> workingEmployeeEntities = new HashSet<>();

    @ManyToMany
    private Set<EmployeeEntity> employeesOnVacation = new HashSet<>();

    @ManyToMany
    private Set<EmployeeEntity> sickEmployeeEntities = new HashSet<>();

    @ManyToMany
    private Set<EmployeeEntity> absentEmployeeEntities = new HashSet<>();

    public WorkingDay(){}

    public WorkingDay(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<EmployeeEntity> getWorkingEmployees() {
        return workingEmployeeEntities;
    }

    public void setWorkingEmployees(Set<EmployeeEntity> workingEmployeeEntities) {
        this.workingEmployeeEntities = workingEmployeeEntities;
    }

    public Set<EmployeeEntity> getEmployeesOnVacation() {
        return employeesOnVacation;
    }

    public void setEmployeesOnVacation(Set<EmployeeEntity> employeesOnVacation) {
        this.employeesOnVacation = employeesOnVacation;
    }

    public Set<EmployeeEntity> getSickEmployees() {
        return sickEmployeeEntities;
    }

    public void setSickEmployees(Set<EmployeeEntity> sickEmployeeEntities) {
        this.sickEmployeeEntities = sickEmployeeEntities;
    }

    public Set<EmployeeEntity> getAbsentEmployees() {
        return absentEmployeeEntities;
    }

    public void setAbsentEmployees(Set<EmployeeEntity> absentEmployeeEntities) {
        this.absentEmployeeEntities = absentEmployeeEntities;
    }
}
