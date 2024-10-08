package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import be.pxl.services.repository.IDepartmentRepository;
import be.pxl.services.services.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DepartmentApplicationService.class)
@Testcontainers
@AutoConfigureMockMvc
public class DepartmentTests {
    private Department department;
    private Department department2;
    private Department department3;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        departmentRepository.deleteAll();

        department = Department.builder()
                .name("PXL")
                .position("Test")
                .organizationId(1L)
                //.employees(List.of(new Employee(1L, 1L, 1L, "Emiel Willems", 20, "Student")))
                .build();

        department2 = Department.builder()
                .name("PSD Diepenbeek")
                .position("Test")
                .organizationId(2L)
                .build();

        department3 = Department.builder()
                .name("Paleis")
                .position("Test")
                .organizationId(2L)
                .build();
    }

    @Test
    public void testAddDepartment() throws Exception {
        String departmentString = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(departmentString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertEquals(1, departmentRepository.findAll().size());
    }

    @Test
    public void testFindDepartmentById() throws Exception {
        departmentRepository.save(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/{id}", department.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(department.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(department.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(department.getPosition()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindAllDepartments() throws Exception {
        departmentRepository.save(department);
        departmentRepository.save(department2);
        departmentRepository.save(department3);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(department.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(department.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(department2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(department2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(department3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(department3.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindByOrganization() throws Exception {
        departmentRepository.save(department);
        departmentRepository.save(department2);
        departmentRepository.save(department3);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/{organizationId}", department2.getOrganizationId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(department2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(department2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(department3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(department3.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //TODO
    @Test
    public void testFindByOrganizationWithEmployees() throws Exception {}
}
