package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.repository.IDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements  IDepartmentService {

    private IDepartmentRepository departmentRepository;

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .organizationId(departmentRequest.getOrganizationId())
                .name(departmentRequest.getName())
                .employees(departmentRequest.getEmployees())
                .position(departmentRequest.getPosition())
                .build();

        departmentRepository.save(department);
    }

    @Override
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            return mapToDepartmentResponse(department);
        }
        return null;
    }

    @Override
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll().stream()
                .map(employee -> mapToDepartmentResponse(employee))
                .toList();
    }

    @Override
    public List<DepartmentResponse> findByOrganization(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream()
                .map(employee -> mapToDepartmentResponse(employee))
                .toList();
    }

    //not finished
    @Override
    public List<DepartmentResponse> findByOrganizationWithEmployees(Long organizationId) {
        return List.of();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .employees(department.getEmployees())
                .position(department.getPosition())
                .build();
    }
}
