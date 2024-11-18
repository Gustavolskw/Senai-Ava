package senai.com.backend_atividades.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank(message = "Nome de Usuario deve ser preenchido")
        String name,
        @NotBlank(message = "Nome de Usuario deve ser preenchido")
        @Email(message = "Email deve ser Válido!")
        String email,
        @NotBlank(message = "Senha deve ser preenchido!")
        String password,
        String cpf,
        Long roleId,
        String nameImage
        ) {
}
