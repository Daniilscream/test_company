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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;
import ru.aisa.test_company.restservice.dto.DepartmentDTO;
import ru.aisa.test_company.restservice.model.Department;
import ru.aisa.test_company.restservice.model.Employee;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Validated
@Api(value = "department", description = "the department API")
public class DepartmentController {

    private final NativeWebRequest request;

    @Autowired
    public DepartmentController(NativeWebRequest request) {
        this.request = request;
    }

    @Resource
    private DepartmentRepository departmentRepository;

    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /department/add : Добавление нового отдела
     *
     * @param department Добавление нового сотрудника (required)
     * @return successful operation (status code 200)
     *         or Invalid parameters (status code 400)
     */
    @ApiOperation(value = "Добавление нового отдела", nickname = "addDepartment", notes = "", tags={ "department", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid parameters") })
    @PostMapping(
            value = "/department/add"
    )
    public ResponseEntity<Void> addDepartment(
            @ApiParam(value = "Добавление нового сотрудника" ,required=true )
            @Valid @RequestBody Department department) {
        if(department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        Department newDepartment = new Department();
        newDepartment.setNameDepartment(department.getNameDepartment());
        departmentRepository.save(newDepartment);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * DELETE /department/delete/{departmentId} : Удаление отдела
     *
     * @param departmentId Id отдела (required)
     * @return Invalid ID supplied (status code 400)
     *         or Department not found (status code 404)
     */
    @ApiOperation(value = "Удаление отдела", nickname = "deleteDepartment", notes = "", tags={ "department", })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Department not found") })
    @DeleteMapping(
            value = "/department/delete/{departmentId}"
    )
    public ResponseEntity<Void> deleteDepartment(@Min(1L)@ApiParam(value = "Id отдела",required=true) @PathVariable("departmentId") Long departmentId) {
        if(departmentId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        Department department = departmentRepository.findById(departmentId).get();
        departmentRepository.delete(department);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    /**
     * GET /department/workinghours/{departmentId} : Количество рабочих часов отдела
     *
     * @param departmentId Id отдела (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "Количество рабочих часов отдела", nickname = "getWorkingHours", notes = "", tags={ "department", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    @GetMapping(
            value = "/department/workinghours/{departmentId}"
    )
    public ResponseEntity<Long> getWorkingHours(@Min(1L)@ApiParam(value = "Id отдела",required=true) @PathVariable("departmentId") Long departmentId) {
        if(departmentId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        Department department = departmentRepository.findById(departmentId).get();
        long time = 0;
        for (Employee e : department.getEmployees()) {
            time += e.getTime();
        }
        return new ResponseEntity<>(time,HttpStatus.NOT_IMPLEMENTED);
    }


    /**
     * GET /department/infoAllDepartment : Информация по отделам
     *
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "Информация по отделам", nickname = "infoAllDepartment", notes = "", response = Department.class, responseContainer = "List", tags={ "department", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Department.class, responseContainer = "List") })
    @GetMapping(
            value = "/department/infoAllDepartment",
            produces = { "application/json" }
    )
    public ResponseEntity<List<DepartmentDTO>> infoAllDepartment() {
        List<Department> departments = (List<Department>) departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();
        for (Department department: departments) {
            departmentDTOS.add(new DepartmentDTO(department));
        }
        return new ResponseEntity<>(departmentDTOS,HttpStatus.OK);

    }
}
