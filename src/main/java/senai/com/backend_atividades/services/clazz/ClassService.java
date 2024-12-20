package senai.com.backend_atividades.services.clazz;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senai.com.backend_atividades.domain.turma.Class;
import senai.com.backend_atividades.domain.turma.ClassRegisterDTO;
import senai.com.backend_atividades.domain.turma.ClassResponseDTO;
import senai.com.backend_atividades.exception.AlreadyExistsException;
import senai.com.backend_atividades.exception.NotFoundException;
import senai.com.backend_atividades.exception.NullListException;
import senai.com.backend_atividades.repository.TurmaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassService implements IClassService {

    private final TurmaRepository turmaRepository;

    @Override
    public ClassResponseDTO createClass(ClassRegisterDTO classRegisterDTO) {

        return Optional.of(classRegisterDTO)
                .filter(turma -> !turmaRepository.existsByName(classRegisterDTO.name()))
                .map(registro -> {

                    Class clazz = new Class();

                    clazz.setName(classRegisterDTO.name());
                    clazz.setStartDate(classRegisterDTO.startDate());
                    clazz.setFinalDate(classRegisterDTO.finalDate());
                    clazz.setImgClass(classRegisterDTO.imgClass());

                    turmaRepository.save(clazz);

                    return new ClassResponseDTO(clazz);

                }).orElseThrow(() -> new AlreadyExistsException("Turma ja Existente"));

    }

    @Override
    public List<ClassResponseDTO> getTurmas() {

        return Optional.of(turmaRepository.findAll()
                        .stream()
                        .map(ClassResponseDTO::new).toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NullListException("Lista de Turmas esta Vazia!"));

    }

    @Override
    public ClassResponseDTO getTurmaById(Long turmaId) {

        return Optional.of(turmaId)
                .filter(turma -> turmaRepository.existsById(turmaId))
                .map(turma -> new ClassResponseDTO(turmaRepository.getReferenceById(turmaId)))
                .orElseThrow(() -> new NotFoundException("Turma não econtrada pelo id:" + turmaId + "!"));

    }

    @Override
    @Transactional
    public ClassResponseDTO updateClass(ClassRegisterDTO clazzEdit, Long turmaId) {

        Class clazz = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new NotFoundException("Turma para edição não encontrada!"));

        boolean existsWithSameNome = turmaRepository.existsByNameLikeAndIdNot(clazzEdit.name(), turmaId);

        if (existsWithSameNome) {
            throw new AlreadyExistsException("Turma com esse Nome já existe");
        }

        clazz.setName(clazzEdit.name());
        clazz.setStartDate(clazzEdit.startDate());
        clazz.setFinalDate(clazzEdit.finalDate());
        clazz.setImgClass(clazzEdit.imgClass());

        turmaRepository.save(clazz);

        return new ClassResponseDTO(clazz);

    }

    @Override
    public void deleteTurma(Long turmaId) {

        turmaRepository.findById(turmaId)
                .ifPresentOrElse(turmaRepository::delete,
                        () -> {
                            throw new NotFoundException("Turma para deletar não encontrada");
                        });

    }

}
