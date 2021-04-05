package ru.aisa.test_company.restservice.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class WorkingDay {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    private Date date;

    @OneToMany
    private List<Employee> workingEmployees = new ArrayList<>();

    @OneToMany
    private List<Employee> employeesOnVacation = new ArrayList<>();

    @OneToMany
    private List<Employee> sickEmployees = new ArrayList<>();

    @OneToMany
    private List<Employee> absentEmployees = new ArrayList<>();

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

    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    public List<Employee> getEmployeesOnVacation() {
        return employeesOnVacation;
    }

    public void setEmployeesOnVacation(List<Employee> employeesOnVacation) {
        this.employeesOnVacation = employeesOnVacation;
    }

    public List<Employee> getSickEmployees() {
        return sickEmployees;
    }

    public void setSickEmployees(List<Employee> sickEmployees) {
        this.sickEmployees = sickEmployees;
    }

    public List<Employee> getAbsentEmployees() {
        return absentEmployees;
    }

    public void setAbsentEmployees(List<Employee> absentEmployees) {
        this.absentEmployees = absentEmployees;
    }
}
