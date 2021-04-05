package ru.aisa.test_company.restservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;
import ru.aisa.test_company.restservice.dto.EmployeeDTO;
import ru.aisa.test_company.restservice.model.Department;
import ru.aisa.test_company.restservice.model.Employee;
import ru.aisa.test_company.restservice.model.WorkingDay;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;
import ru.aisa.test_company.restservice.repository.EmployeeRepository;
import ru.aisa.test_company.restservice.repository.WorkingDayRepository;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@Validated
@Api(value = "employee", description = "the employee API")
public class EmployeeController {

    private final NativeWebRequest request;

    @Autowired
    public EmployeeController(NativeWebRequest request) {
        this.request = request;
    }

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private WorkingDayRepository workingDayRepository;

    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @GetMapping(value = "/employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employeeId") Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).get();
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        return new ResponseEntity<>(employeeDTO,HttpStatus.OK);
    }

    /**
     * POST /employee/add : Добавление нового сотрудника
     *
     * @param employee Добавление нового сотрудника (required)
     * @return successful operation (status code 200)
     *         or Invalid parameters (status code 400)
     */
    @ApiOperation(value = "Добавление нового сотрудника", nickname = "addEmployee", notes = "", tags={ "employee", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid parameters") })
    @PostMapping(
            value = "/employee/add"
    )
    public ResponseEntity<Void> addEmployee(
            @ApiParam(value = "Добавление нового сотрудника" ,required=true )  @Valid @RequestBody Employee employee) {
        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        Employee newEmployee = new Employee();
        Department department = departmentRepository.findById(employee.getDepartment().getId()).get();
        newEmployee.setDateOfReceipt(employee.getDateOfReceipt());
        newEmployee.setFullName(employee.getFullName());
        newEmployee.setPosition(employee.getPosition());
        newEmployee.setDepartmentId(department);
        employeeRepository.save(newEmployee);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * DELETE /employee/delete/{employeeId} : Удаление сотрудника
     *
     * @param employeeId Id сотрудника (required)
     * @return Invalid ID supplied (status code 400)
     *         or Employee not found (status code 404)
     */
    @ApiOperation(value = "Удаление сотрудника", nickname = "deleteEmployee", notes = "", tags={ "employee", })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Employee not found") })
    @DeleteMapping(
            value = "/employee/delete/{employeeId}"
    )
    public ResponseEntity<Void> deleteEmployee(@Min(1L)@ApiParam(value = "Id сотрудника",required=true) @PathVariable("employeeId") Long employeeId) {
        if(employeeId == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Employee employee = employeeRepository.findById(employeeId).get();
        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * GET /employee/lastvacation/{employeeId} :  Дата последнего отпуска
     *
     * @param employeeId Id сотрудника (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = " Дата последнего отпуска", nickname = "getLastVacation", notes = "", tags={ "employee", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    @GetMapping(
            value = "/employee/lastvacation/{employeeId}"
    )
    public ResponseEntity<String> getLastVacation(@Min(1L)@ApiParam(value = "Id сотрудника",required=true) @PathVariable("employeeId") Long employeeId) {
        if(employeeId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        Employee employee = employeeRepository.findById(employeeId).get();
        Date date = workingDayRepository.findByLastVacationDay(employee);
        if(date == null){
            return new ResponseEntity<>("Сотрудник еще не был в отпуске",HttpStatus.OK);
        }
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        return new ResponseEntity<>(formatDate.format(date),HttpStatus.OK);
    }


    /**
     * GET /employee/workingdays/{employeeId} : Количество рабочих дней сотрудника
     *
     * @param employeeId Id сотрудника (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "Количество рабочих дней сотрудника", nickname = "getWorkingDays", notes = "", tags={ "employee", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    @GetMapping(
            value = "/employee/workingdays/{employeeId}"
    )
    public ResponseEntity<Integer> getWorkingDays(@Min(1L)@ApiParam(value = "Id сотрудника",required=true) @PathVariable("employeeId") Long employeeId) {
        if(employeeId == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Employee employee = employeeRepository.findById(employeeId).get();
        Integer workingDays =  workingDayRepository.findByWorkingDays(employee);
        return new ResponseEntity<>(workingDays,HttpStatus.OK);

    }


    /**
     * PUT /employee/vacationOrSickLeave : Отметка больничного или отпуска
     *
     * @param employee Отметка больничного или отпуска (optional)
     * @return successful operation (status code 200)
     *         or Invalid IDs (status code 400)
     */
    @ApiOperation(value = "Отметка больничного или отпуска", nickname = "vacationOrSickLeave", notes = "", tags={ "employee", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid IDs") })
    @PutMapping(
            value = "/employee/vacationOrSickLeave"
    )
    public ResponseEntity<Void> vacationOrSickLeave(
            @ApiParam(value = "Отметка больничного или отпуска"  )
            @Valid @RequestBody(required = false) Employee employee) {
        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Employee selectedEmployee = employeeRepository.findById(employee.getId()).get();
        WorkingDay workingDay = workingDayRepository.findByDate(employee.getDate());
        if(workingDay == null){
            workingDay = new WorkingDay(employee.getDate());
            workingDayRepository.save(workingDay);
            workingDay = workingDayRepository.findByDate(selectedEmployee.getDate());
        }
        selectedEmployee.setDate(employee.getDate());
        if(employee.getDaysForVacation() != 0) {
            selectedEmployee.setDaysForVacation(employee.getDaysForVacation());
            workingDay.getEmployeesOnVacation().add(selectedEmployee);
        }
        if(employee.getDaysForSickLeave() != 0) {
            selectedEmployee.setDaysForSickLeave(employee.getDaysForSickLeave());
            workingDay.getSickEmployees().add(selectedEmployee);
        }
        workingDayRepository.save(workingDay);
        employeeRepository.save(selectedEmployee);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * PUT /employee/visitOrWalk : Отметка посещения или прогула
     *
     * @param employee Отметка посещения или прогула (optional)
     * @return successful operation (status code 200)
     *         or Invalid IDs (status code 400)
     */
    @ApiOperation(value = "Отметка посещения или прогула", nickname = "visitOrWalk", notes = "", tags={ "employee", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid IDs") })
    @PutMapping(
            value = "/employee/visitOrWalk"
    )
    public ResponseEntity<Void> visitOrWalk(
            @ApiParam(value = "Отметка посещения или прогула"  )
            @Valid @RequestBody(required = false) Employee employee) {
        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        Employee selectedEmployee = employeeRepository.findById(employee.getId()).get();
        WorkingDay workingDay = workingDayRepository.findByDate(employee.getDate());
        if(workingDay == null){
            workingDay = new WorkingDay(employee.getDate());
            workingDayRepository.save(workingDay);
            workingDay = workingDayRepository.findByDate(selectedEmployee.getDate());
        }
        selectedEmployee.setDate(employee.getDate());
        selectedEmployee.setVisit(employee.isVisit());
        if(employee.isVisit()){
            workingDay.getWorkingEmployees().add(selectedEmployee);
            selectedEmployee.setTime(selectedEmployee.getTime() + employee.getTime());
        } else {
            workingDay.getAbsentEmployees().add(selectedEmployee);
        }
        workingDayRepository.save(workingDay);
        employeeRepository.save(selectedEmployee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
