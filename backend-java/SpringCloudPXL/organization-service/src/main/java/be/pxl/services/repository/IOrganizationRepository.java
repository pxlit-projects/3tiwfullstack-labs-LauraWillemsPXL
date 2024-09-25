package be.pxl.services.repository;

import be.pxl.services.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrganizationRepository extends JpaRepository<Organization, Long> {
}
