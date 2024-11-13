package be.pxl.services.controller;

import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        logger.debug("Adding a new employee with details: {}", employeeRequest);
        employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findEmployeeById(@PathVariable Long id) {
        logger.debug("Finding employee with id {}", id);
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByDepartmentId(@PathVariable Long departmentId) {
        return new ResponseEntity<>(employeeService.findByDepartment(departmentId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByOrganizationId(@PathVariable Long organizationId) {
        return new ResponseEntity<>(employeeService.findByOrganization(organizationId), HttpStatus.OK);
    }
}
