package senai.com.backend_atividades.domain.turma;

public record ClassResponseDTO(Long Id, String nome ) {
    public ClassResponseDTO(Class turma) {
        this(turma.getId(), turma.getName());
    }
}
