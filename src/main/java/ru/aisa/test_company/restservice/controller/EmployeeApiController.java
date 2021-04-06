package ru.aisa.test_company.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.restservice.model.WorkingDay;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;
import ru.aisa.test_company.restservice.repository.EmployeeRepository;
import ru.aisa.test_company.restservice.repository.WorkingDayRepository;
import ru.aisa.test_company.swagger.api.EmployeeApi;
import ru.aisa.test_company.swagger.model.Employee;
import ru.aisa.test_company.swagger.model.VacationOrSickLeave;
import ru.aisa.test_company.swagger.model.VisitOrWalk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-05T18:34:06.116+03:00")

@Controller
public class EmployeeApiController implements EmployeeApi {

    private static final Logger log = LoggerFactory.getLogger(EmployeeApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private WorkingDayRepository workingDayRepository;

    @Autowired
    public EmployeeApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addEmployee(@ApiParam(value = "Добавление нового сотрудника" ,required=true )  @Valid @RequestBody Employee employee,
                                            @NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "department_id", required = true)
                                                    Long departmentId) {
        String accept = request.getHeader("Accept");
        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        EmployeeEntity newEmployeeEntity = new EmployeeEntity();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        try {
            newEmployeeEntity.setDateOfReceipt(formatDate.parse(employee.getDateOfReceipt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newEmployeeEntity.setFullName(employee.getFullName());
        newEmployeeEntity.setPosition(employee.getPosition());
        newEmployeeEntity.setDepartmentId(departmentEntity);
        employeeRepository.save(newEmployeeEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteEmployee(@Min(1L)@ApiParam(value = "Id сотрудника",required=true) @PathVariable("employeeId") Long employeeId) {
        String accept = request.getHeader("Accept");
        if(employeeId == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        employeeRepository.delete(employeeEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> getLastVacation(@Min(1L)@ApiParam(value = "Id сотрудника",required=true) @PathVariable("employeeId") Long employeeId) {
        String accept = request.getHeader("Accept");
        if(employeeId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        Date date = workingDayRepository.findByLastVacationDay(employeeEntity);
        if(date == null){
            return new ResponseEntity<>("Сотрудник еще не был в отпуске",HttpStatus.OK);
        }
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return new ResponseEntity<>(formatDate.format(date),HttpStatus.OK);
    }

    public ResponseEntity<Integer> getWorkingDays(@Min(1L)@ApiParam(value = "Id сотрудника",required=true) @PathVariable("employeeId") Long employeeId) {
        String accept = request.getHeader("Accept");
        if(employeeId == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        Integer workingDays =  workingDayRepository.findByWorkingDays(employeeEntity);
        return new ResponseEntity<>(workingDays,HttpStatus.OK);
    }

    public ResponseEntity<Void> vacationOrSickLeave(@ApiParam(value = "Отметка больничного или отпуска"  )
                                                    @Valid @RequestBody VacationOrSickLeave vacationOrSickLeave) {
        String accept = request.getHeader("Accept");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        if(vacationOrSickLeave == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        EmployeeEntity selectedEmployeeEntity = employeeRepository.findById(vacationOrSickLeave.getId()).get();
        WorkingDay workingDay = null;
        try {
            workingDay = workingDayRepository.findByDate(formatDate.parse(vacationOrSickLeave.getDate()));
        if(workingDay == null){
            workingDay = new WorkingDay(formatDate.parse(vacationOrSickLeave.getDate()));
            workingDayRepository.save(workingDay);
            workingDay = workingDayRepository.findByDate(formatDate.parse(vacationOrSickLeave.getDate()));
        }
        selectedEmployeeEntity.setDate(formatDate.parse(vacationOrSickLeave.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(vacationOrSickLeave.getDaysForVacation() != 0) {
            selectedEmployeeEntity.setDaysForVacation(vacationOrSickLeave.getDaysForVacation());
            workingDay.getEmployeesOnVacation().add(selectedEmployeeEntity);
        }else if(vacationOrSickLeave.getDaysForSickLeave() != 0) {
            selectedEmployeeEntity.setDaysForSickLeave(vacationOrSickLeave.getDaysForSickLeave());
            workingDay.getSickEmployees().add(selectedEmployeeEntity);
        }
        workingDayRepository.save(workingDay);
        employeeRepository.save(selectedEmployeeEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> visitOrWalk(@ApiParam(value = "Отметка посещения или прогула"  )  @Valid @RequestBody VisitOrWalk visitOrWalk) {
        String accept = request.getHeader("Accept");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        if(visitOrWalk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        EmployeeEntity selectedEmployeeEntity = employeeRepository.findById(visitOrWalk.getId()).get();
        WorkingDay workingDay = null;
        try {
            workingDay = workingDayRepository.findByDate(formatDate.parse(visitOrWalk.getDate()));
        if(workingDay == null){
            workingDay = new WorkingDay(formatDate.parse(visitOrWalk.getDate()));
            workingDayRepository.save(workingDay);
            workingDay = workingDayRepository.findByDate(formatDate.parse(visitOrWalk.getDate()));
        }
        selectedEmployeeEntity.setDate(formatDate.parse(visitOrWalk.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        selectedEmployeeEntity.setVisit(visitOrWalk.isVisit());
        if(visitOrWalk.isVisit()){
            workingDay.getWorkingEmployees().add(selectedEmployeeEntity);
            selectedEmployeeEntity.setTime(selectedEmployeeEntity.getTime() + visitOrWalk.getTime());
        } else {
            workingDay.getAbsentEmployees().add(selectedEmployeeEntity);
        }
        workingDayRepository.save(workingDay);
        employeeRepository.save(selectedEmployeeEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
