package be.pxl.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.IEmployeeRepository;
import be.pxl.services.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EmployeeServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
public class EmployeeTests {
    private Employee employee;
    private Employee employee2;
    private Employee employee3;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");

    @Autowired
    private EmployeeService employeeService;

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();

        employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("Student")
                .departmentId(1L)
                .organizationId(1L)
                .build();

        employee2 = Employee.builder()
                .age(17)
                .name("Sara")
                .position("Student")
                .departmentId(2L)
                .organizationId(2L)
                .build();

        employee3 = Employee.builder()
                .age(20)
                .name("Emiel")
                .position("Student")
                .departmentId(2L)
                .organizationId(1L)
                .build();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        String employeeString = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeString))
                .andExpect(status().isCreated());

        assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    public void testFindEmployeeById() throws Exception {
        employeeRepository.save(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(employee.getPosition()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(employee2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employee2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(employee3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(employee3.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmployeeByDepartmentId() throws Exception {
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/{departmentId}", 2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(employee3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employee3.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmployeeByOrganization() throws Exception {
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/{organizationId}", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(employee3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employee3.getName()))
                .andExpect(status().isOk());
    }
}
