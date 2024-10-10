package senai.com.backend_atividades.services.turma;

import senai.com.backend_atividades.domain.turma.TurmaRegisterDTO;
import senai.com.backend_atividades.domain.turma.TurmaResponseDTO;

import java.util.List;

public interface ITurmaService {

    TurmaResponseDTO createTurma(TurmaRegisterDTO turmaRegisterDTO);
    List<TurmaResponseDTO> getTurmas();
    TurmaResponseDTO getTurmaById(Long turmaId);
    TurmaResponseDTO updateTurma(String nome, Long turmaId);
    void deleteTurma(Long turmaId);
}
