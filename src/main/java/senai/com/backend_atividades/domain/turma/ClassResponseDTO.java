package senai.com.backend_atividades.domain.turma;

import java.time.LocalDate;

public record ClassResponseDTO(Long Id, String nome, LocalDate startDate, LocalDate finalDate, String imgClass) {
    public ClassResponseDTO(Class clazz) {
        this(clazz.getId(), clazz.getName(), clazz.getStartDate(), clazz.getFinalDate(), clazz.getImgClass());
    }
}
