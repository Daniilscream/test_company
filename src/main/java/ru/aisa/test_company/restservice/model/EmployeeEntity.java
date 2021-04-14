package ru.aisa.test_company.restservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@Entity
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfReceipt;

    @ApiModelProperty(value = "")
    private String fullName;

    @ApiModelProperty(value = "")
    private String position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @ApiModelProperty(value = "")
    private DepartmentEntity departmentEntity;

    @ApiModelProperty(value = "")
    @Valid
    private int daysForVacation;

    @ApiModelProperty(value = "")
    @Valid
    private int daysForSickLeave;

    @ApiModelProperty(value = "")
    private Date date;

    @ManyToMany
    private Set<WorkingDay> workingDays;

    @ApiModelProperty(value = "")
    private boolean visit;

    @JsonProperty("time")
    private int time = 0;

    /**
     * Get id
     * @return id
     **/
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeEntity id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get dateOfReceipt
     * @return dateOfReceipt
     **/
    @JsonProperty("dateOfReceipt")
    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public EmployeeEntity dateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
        return this;
    }

    /**
     * Get fullName
     * @return fullName
     **/
    @JsonProperty("FullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public EmployeeEntity fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    /**
     * Get position
     * @return position
     **/
    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public EmployeeEntity position(String position) {
        this.position = position;
        return this;
    }

    /**
     * Get departmentId
     * @return departmentId
     **/
    @JsonProperty("department_id")
    public DepartmentEntity getDepartment() {
        return departmentEntity;
    }

    public void setDepartmentId(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public String getDepartmentName(){
        if(departmentEntity == null){
            return "";
        }
        return departmentEntity.getNameDepartment();
    }

    public EmployeeEntity departmentId(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
        return this;
    }

    public int getDaysForVacation() {
        return daysForVacation;
    }

    public void setDaysForVacation(int daysForVacation) {
        this.daysForVacation = daysForVacation;
    }

    public int getDaysForSickLeave() {
        return daysForSickLeave;
    }

    public void setDaysForSickLeave(int daysForSickLeave) {
        this.daysForSickLeave = daysForSickLeave;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Set<WorkingDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Set<WorkingDay> workingDays) {
        this.workingDays = workingDays;
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
