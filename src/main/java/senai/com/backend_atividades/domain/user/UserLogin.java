package senai.com.backend_atividades.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserLogin(@NotBlank
                        String email,
                        @NotBlank
                        String password) {
}
