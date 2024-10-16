package be.pxl.services.services;

import be.pxl.services.domain.dto.OrganizationRequest;
import be.pxl.services.domain.dto.OrganizationResponse;

import java.util.List;

public interface IOrganizationService {
    void addOrganization(OrganizationRequest organizationRequest);
    OrganizationResponse findById(Long id);
    OrganizationResponse findByIdWithDepartments(Long id);
    OrganizationResponse findByIdWithDepartmentsAndEmployees(Long id);
    OrganizationResponse findByIdWithEmployees(Long id);
}
