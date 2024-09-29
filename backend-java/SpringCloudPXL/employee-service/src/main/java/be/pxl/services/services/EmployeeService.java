package be.pxl.services.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .organizationId(employeeRequest.getOrganizationId())
                .departmentId(employeeRequest.getDepartmentId())
                .name(employeeRequest.getName())
                .age(employeeRequest.getAge())
                .position(employeeRequest.getPosition())
                .build();

        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            return mapToEmployeeResponse(employee);
        }
        return null;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> mapToEmployeeResponse(employee)).toList();
    }

    @Override
    public List<EmployeeResponse> findByDepartment(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream().map(employee -> mapToEmployeeResponse(employee)).toList();
    }

    @Override
    public List<EmployeeResponse> findByOrganization(Long organizationId) {
        List<Employee> employees = employeeRepository.findByOrganizationId(organizationId);
        return employees.stream().map(employee -> mapToEmployeeResponse(employee)).toList();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .organizationId(employee.getOrganizationId())
                .departmentId(employee.getDepartmentId())
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .build();
    }
}
