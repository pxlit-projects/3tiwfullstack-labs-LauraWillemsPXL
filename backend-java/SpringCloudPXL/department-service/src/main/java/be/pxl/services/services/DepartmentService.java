package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.IDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .organizationId(departmentRequest.getOrganizationId())
                .name(departmentRequest.getName())
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
                .map(department -> mapToDepartmentResponse(department))
                .toList();
    }

    @Override
    public List<DepartmentResponse> findByOrganization(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream()
                .map(department -> mapToDepartmentResponse(department))
                .toList();
    }

    //not finished
    @Override
    public List<DepartmentResponse> findByOrganizationWithEmployees(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream()
                .map(department -> mapToDepartmentResponseWithEmployees(department))
                .toList();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }

    private DepartmentResponse mapToDepartmentResponseWithEmployees(Department department) {
        return DepartmentResponse.builder()
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .employees(department.getEmployees())
                .build();
    }
}
