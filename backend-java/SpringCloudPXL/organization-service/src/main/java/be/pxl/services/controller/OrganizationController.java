package be.pxl.services.controller;

import be.pxl.services.domain.dto.OrganizationRequest;
import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.services.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrganization(@RequestBody OrganizationRequest organizationRequest) {
        organizationService.addOrganization(organizationRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(organizationService.findById(id), HttpStatus.OK);
    }

    //TODO
    @GetMapping("/{id}/with-departments")
    public ResponseEntity<OrganizationResponse> findByIdWithDepartments(@PathVariable Long id) {
        return new ResponseEntity<>(organizationService.findById(id), HttpStatus.OK);
    }

    //TODO
    @GetMapping("/{id}/with-departments-and-employees")
    public ResponseEntity<OrganizationResponse> findByIdWithDepartmentsAndEmployees(@PathVariable Long id) {
        return new ResponseEntity<>(organizationService.findByIdWithDepartmentsAndEmployees(id), HttpStatus.OK);
    }

    //TODO
    @GetMapping("/{id}/with-employees")
    public ResponseEntity<OrganizationResponse> findByIdWithEmployees(@PathVariable Long id) {
        return new ResponseEntity<>(organizationService.findByIdWithEmployees(id), HttpStatus.OK);
    }
}
