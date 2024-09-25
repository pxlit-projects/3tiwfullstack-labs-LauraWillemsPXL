package be.pxl.services.services;

import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.repository.IOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {

    private IOrganizationRepository organizationRepository;

    @Override
    public OrganizationResponse findById(Long id) {
        return null;
    }

    @Override
    public OrganizationResponse findByIdWithDepartments(Long id) {
        return null;
    }

    @Override
    public OrganizationResponse findByIdWithDepartmentsAndEmployees(Long id) {
        return null;
    }

    @Override
    public OrganizationResponse findByIdWithEmployees(Long id) {
        return null;
    }
}
