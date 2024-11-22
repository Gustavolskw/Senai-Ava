package senai.com.backend_atividades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai.com.backend_atividades.domain.turma.Class;

public interface TurmaRepository  extends JpaRepository<Class, Long> {
    boolean existsByName(String turmaNome);

    boolean existsByNameLikeAndIdNot(String nome, Long turmaId);
}
