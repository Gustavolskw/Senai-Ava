package senai.com.backend_atividades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai.com.backend_atividades.domain.turma.Turma;

public interface TurmaRepository  extends JpaRepository<Turma, Long> {
    boolean existsByNome(String turmaNome);

    boolean existsByNomeLikeAndIdNot(String nome, Long turmaId);
}
