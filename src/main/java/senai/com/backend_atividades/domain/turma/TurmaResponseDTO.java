package senai.com.backend_atividades.domain.turma;

public record TurmaResponseDTO(Long Id, String nome ) {
    public TurmaResponseDTO(Turma turma) {
        this(turma.getId(), turma.getNome());
    }
}
