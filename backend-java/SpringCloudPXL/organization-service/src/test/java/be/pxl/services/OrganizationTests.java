package be.pxl.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.repository.IOrganizationRepository;
import be.pxl.services.services.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(classes = OrganizationServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
public class OrganizationTests {
    private Organization organization;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private IOrganizationRepository organizationRepository;

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
        organizationRepository.deleteAll();

        organization = Organization.builder()
                .name("Organization1")
                .address("test")
                .build();
    }

    @Test
    public void testFindById() throws Exception {
        organizationRepository.save(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/{id}", organization.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(organization.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(organization.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(organization.getAddress()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //TODO
    @Test
    public void testFindByIdWithDepartments() {}

    //TODO
    @Test
    public void testFindByIdWithDepartmentsAndEmployees() {}

    //TODO
    @Test
    public void testFindByIdWithEmployees() {}
}
