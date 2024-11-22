package senai.com.backend_atividades.services.clazz;

import senai.com.backend_atividades.domain.turma.ClassRegisterDTO;
import senai.com.backend_atividades.domain.turma.ClassResponseDTO;

import java.util.List;

public interface IClassService {

    ClassResponseDTO createTurma(ClassRegisterDTO classRegisterDTO);
    List<ClassResponseDTO> getTurmas();
    ClassResponseDTO getTurmaById(Long turmaId);
    ClassResponseDTO updateTurma(String nome, Long turmaId);
    void deleteTurma(Long turmaId);
}
