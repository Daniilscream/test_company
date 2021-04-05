package ru.aisa.test_company.restservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    @JsonProperty("nameDepartment")
    private String nameDepartment;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    @JsonProperty("employees")
    @Valid
    private List<Employee> employees;

    @JsonProperty("workingHours")
    private Long workingHours;

    @JsonProperty("presentEmployees")
    private int presentEmployees;

    @JsonProperty("missingEmployees")
    private int missingEmployees;

    public Department id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     */
    @ApiModelProperty(value = "")


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department nameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
        return this;
    }

    /**
     * Get nameDepartment
     * @return nameDepartment
     */
    @ApiModelProperty(value = "")


    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public Department employees(List<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Department addEmployeesItem(Employee employeesItem) {
        if (this.employees == null) {
            this.employees = new ArrayList<>();
        }
        this.employees.add(employeesItem);
        return this;
    }

    /**
     * Get employees
     * @return employees
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Department workingHours(Long workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    /**
     * Get workingHours
     * @return workingHours
     */
    @ApiModelProperty(value = "")


    public Long getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Long workingHours) {
        this.workingHours = workingHours;
    }

    public Department presentEmployees(Integer presentEmployees) {
        this.presentEmployees = presentEmployees;
        return this;
    }

    /**
     * Get presentEmployees
     * @return presentEmployees
     */
    @ApiModelProperty(value = "")


    public Integer getPresentEmployees() {
        return presentEmployees;
    }

    public void setPresentEmployees(Integer presentEmployees) {
        this.presentEmployees = presentEmployees;
    }

    public Department missingEmployees(Integer missingEmployees) {
        this.missingEmployees = missingEmployees;
        return this;
    }

    /**
     * Get missingEmployees
     * @return missingEmployees
     */
    @ApiModelProperty(value = "")


    public Integer getMissingEmployees() {
        return missingEmployees;
    }

    public void setMissingEmployees(Integer missingEmployees) {
        this.missingEmployees = missingEmployees;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department infoAllDepartment = (Department) o;
        return Objects.equals(this.id, infoAllDepartment.id) &&
                Objects.equals(this.nameDepartment, infoAllDepartment.nameDepartment) &&
                Objects.equals(this.employees, infoAllDepartment.employees) &&
                Objects.equals(this.workingHours, infoAllDepartment.workingHours) &&
                Objects.equals(this.presentEmployees, infoAllDepartment.presentEmployees) &&
                Objects.equals(this.missingEmployees, infoAllDepartment.missingEmployees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameDepartment, employees, workingHours, presentEmployees, missingEmployees);
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
