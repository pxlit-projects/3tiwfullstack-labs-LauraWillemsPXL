package be.pxl.services.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.repository.IOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {

    private final IOrganizationRepository organizationRepository;

    @Override
    public OrganizationResponse findById(Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization != null) {
            return mapToOrganizationResponse(organization);
        }
        return null;
    }

    //TODO
    @Override
    public OrganizationResponse findByIdWithDepartments(Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization != null) {
            return mapToOrganizationResponseWithDepartments(organization);
        }
        return null;
    }

    //TODO
    @Override
    public OrganizationResponse findByIdWithDepartmentsAndEmployees(Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization != null) {
            return mapToOrganizationResponseWithDepartmentsAndEmployees(organization);
        }
        return null;
    }

    //TODO
    @Override
    public OrganizationResponse findByIdWithEmployees(Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization != null) {
            return mapToOrganizationResponseWithEmployees(organization);
        }
        return null;
    }

    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .build();
    }

    private OrganizationResponse mapToOrganizationResponseWithDepartmentsAndEmployees(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .departments(organization.getDepartments())
                .employees(organization.getEmployees())
                .build();
    }

    private OrganizationResponse mapToOrganizationResponseWithEmployees(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .build();
    }

    private OrganizationResponse mapToOrganizationResponseWithDepartments(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .departments(organization.getDepartments())
                .build();
    }
}
