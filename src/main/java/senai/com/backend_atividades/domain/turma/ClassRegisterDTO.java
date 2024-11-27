package senai.com.backend_atividades.domain.turma;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ClassRegisterDTO(@NotBlank String name, LocalDate startDate, LocalDate finalDate, String imgClass) {
}

