package senai.com.backend_atividades.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import senai.com.backend_atividades.domain.user.User;

import java.util.Optional;
import java.util.function.Function;

public interface UserRepository  extends JpaRepository<User, Long> {

        User findByEmail(String email);
        Optional<User> findById(Long id);

        boolean existsByEmail(@NotBlank(message = "Nome de Usuario deve ser preenchido") @Email(message = "Email deve ser Válido!") String email);
}
