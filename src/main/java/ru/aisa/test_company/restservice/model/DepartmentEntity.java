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
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    @JsonProperty("nameDepartment")
    private String nameDepartment;

    @OneToMany(mappedBy = "departmentEntity", fetch = FetchType.LAZY)
    @JsonProperty("employees")
    @Valid
    private List<EmployeeEntity> employeeEntities;

    public DepartmentEntity id(Long id) {
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

    public DepartmentEntity nameDepartment(String nameDepartment) {
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

    public DepartmentEntity employees(List<EmployeeEntity> employeeEntities) {
        this.employeeEntities = employeeEntities;
        return this;
    }

    public DepartmentEntity addEmployeesItem(EmployeeEntity employeesItem) {
        if (this.employeeEntities == null) {
            this.employeeEntities = new ArrayList<>();
        }
        this.employeeEntities.add(employeesItem);
        return this;
    }

    /**
     * Get employees
     * @return employees
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<EmployeeEntity> getEmployees() {
        return employeeEntities;
    }

    public void setEmployees(List<EmployeeEntity> employeeEntities) {
        this.employeeEntities = employeeEntities;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DepartmentEntity infoAllDepartmentEntity = (DepartmentEntity) o;
        return Objects.equals(this.id, infoAllDepartmentEntity.id) &&
                Objects.equals(this.nameDepartment, infoAllDepartmentEntity.nameDepartment) &&
                Objects.equals(this.employeeEntities, infoAllDepartmentEntity.employeeEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameDepartment, employeeEntities);
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
