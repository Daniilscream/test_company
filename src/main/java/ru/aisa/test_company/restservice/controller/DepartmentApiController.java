package ru.aisa.test_company.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;
import ru.aisa.test_company.restservice.service.DepartmentService;
import ru.aisa.test_company.swagger.api.DepartmentApi;
import ru.aisa.test_company.swagger.model.Department;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-05T18:34:06.116+03:00")

@Controller
public class DepartmentApiController implements DepartmentApi {

    private static final Logger log = LoggerFactory.getLogger(DepartmentApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Resource
    private DepartmentRepository departmentRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public DepartmentApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addDepartment(@ApiParam(value = "Добавление нового сотрудника" ,required=true )  @Valid @RequestBody Department addDepartment) {
        String accept = request.getHeader("Accept");
        if(addDepartment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        DepartmentEntity newDepartmentEntity = new DepartmentEntity();
        newDepartmentEntity.setNameDepartment(addDepartment.getNameDepartment());
        departmentRepository.save(newDepartmentEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteDepartment(@Min(1L)@ApiParam(value = "Id отдела",required=true) @PathVariable("departmentId") Long departmentId) {
        String accept = request.getHeader("Accept");
        if(departmentId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        departmentRepository.delete(departmentEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Long> getWorkingHours(@Min(1L)@ApiParam(value = "Id отдела",required=true) @PathVariable("departmentId") Long departmentId) {
        String accept = request.getHeader("Accept");
        if(departmentId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        long time = 0;
        for (EmployeeEntity e : departmentEntity.getEmployees()) {
            time += e.getTime();
        }
        return new ResponseEntity<>(time,HttpStatus.OK);
    }

    public ResponseEntity<List<Department>> infoAllDepartment() {
        String accept = request.getHeader("Accept");
        List<DepartmentEntity> departmentEntities = (List<DepartmentEntity>) departmentRepository.findAll();
        return new ResponseEntity<>(DepartmentService.convertDepartment(departmentEntities),HttpStatus.OK);
    }

}
