package senai.com.backend_atividades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai.com.backend_atividades.domain.Role.Role;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
