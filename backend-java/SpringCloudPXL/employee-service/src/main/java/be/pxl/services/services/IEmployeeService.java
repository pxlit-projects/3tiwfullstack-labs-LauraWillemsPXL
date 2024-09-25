package be.pxl.services.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    void addEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse findById(Long id);
    List<EmployeeResponse> getAllEmployees();
    List<EmployeeResponse> findByDepartment(Long departmentId);
    List<EmployeeResponse> findByOrganization(Long organizationId);

}
