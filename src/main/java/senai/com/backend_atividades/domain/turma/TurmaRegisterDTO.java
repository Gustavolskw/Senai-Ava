package senai.com.backend_atividades.domain.turma;

import jakarta.validation.constraints.NotBlank;

public record TurmaRegisterDTO(@NotBlank String nome) {
}
