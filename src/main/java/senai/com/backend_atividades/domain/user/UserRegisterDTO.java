package senai.com.backend_atividades.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import senai.com.backend_atividades.domain.Role.Role;

public record UserRegisterDTO(
        @NotBlank(message = "Nome de Usuario deve ser preenchido")
        String name,
        @NotBlank(message = "Nome de Usuario deve ser preenchido")
        @Email(message = "Email deve ser Válido!")
        String email,
        @NotBlank(message = "Senha deve ser preenchido!")
        String password,
        String cpf,
        String nameImage,
        Role role,
        MultipartFile image
        ) {

    public UserRegisterDTO(UserRegisterDTO userRegisterDTO, Role role, MultipartFile multipartFile) {

        this(userRegisterDTO.name(),
             userRegisterDTO.email(),
             userRegisterDTO.password(),
             userRegisterDTO.cpf(),
             userRegisterDTO.nameImage(),
             role,
             multipartFile);

    }

}
