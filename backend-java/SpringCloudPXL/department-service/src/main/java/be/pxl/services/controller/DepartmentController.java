package be.pxl.services.controller;

import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.services.IDepartmentService;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Void> addDepartment(@RequestBody DepartmentRequest departmentRequest) {
        departmentService.addDepartment(departmentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> findDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> findAllDepartments() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<DepartmentResponse>> findByOrganization(@PathVariable Long organizationId) {
        return new ResponseEntity<>(departmentService.findByOrganization(organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public ResponseEntity<List<DepartmentResponse>> findByOrganizationWithEmployees(@PathVariable Long organizationId) {
        return new ResponseEntity<>(departmentService.findByOrganizationWithEmployees(organizationId), HttpStatus.OK);
    }
}
