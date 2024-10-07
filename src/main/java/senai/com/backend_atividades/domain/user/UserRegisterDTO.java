package senai.com.backend_atividades.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank(message = "Nome de Usuario deve ser preenchido")
        String nome,
        @NotBlank(message = "Nome de Usuario deve ser preenchido")
        @Email(message = "Email deve ser Válido!")
        String email,
        @NotBlank(message = "Senha deve ser preenchido!")
        String senha) {
}
