package be.pxl.services.domain.dto;

import be.pxl.services.domain.Employee;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    private Long organizationId;
    private String name;
    private String position;
    private List<Employee> employees;
}
