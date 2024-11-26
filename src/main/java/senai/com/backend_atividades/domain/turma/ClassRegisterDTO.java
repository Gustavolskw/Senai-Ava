package senai.com.backend_atividades.domain.turma;

import jakarta.validation.constraints.NotBlank;

public record ClassRegisterDTO(@NotBlank String name) {
}
