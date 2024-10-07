package senai.com.backend_atividades.services.turma;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senai.com.backend_atividades.domain.turma.Turma;
import senai.com.backend_atividades.domain.turma.TurmaRegisterDTO;
import senai.com.backend_atividades.domain.turma.TurmaResponseDTO;
import senai.com.backend_atividades.exception.AlreadyExistsException;
import senai.com.backend_atividades.exception.NotFoundException;
import senai.com.backend_atividades.exception.NullListException;
import senai.com.backend_atividades.repository.TurmaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaService implements ITurmaService {

    private final TurmaRepository turmaRepository;




    @Override
    public TurmaResponseDTO createTurma(TurmaRegisterDTO turmaRegisterDTO) {
        return Optional.of(turmaRegisterDTO)
                .filter(turma-> !turmaRepository.existsByNome(turmaRegisterDTO.nome()))
                .map(registro->{
                    Turma turma = new Turma();
                    turma.setNome(turmaRegisterDTO.nome());
                    turmaRepository.save(turma);
                    return new TurmaResponseDTO(turma);
                }).orElseThrow(()->new AlreadyExistsException("Turma ja Existente"));

    }

    @Override
    public List<TurmaResponseDTO> getTurmas() {
       return  Optional.of(turmaRepository.findAll()
                       .stream()
                       .map(TurmaResponseDTO::new).toList())
               .filter(list -> !list.isEmpty())
               .orElseThrow(() -> new NullListException("Lista de Turmas esta Vazia!"));
    }

    @Override
    public TurmaComAlunosDTO getTurmaById(Long turmaId) {
        return Optional.of(turmaId)
                .filter(turma -> turmaRepository.existsById(turmaId))
                .map(turma -> new TurmaComAlunosDTO(turmaRepository.getReferenceById(turmaId)))
                .orElseThrow(() -> new NotFoundException("Turma não econtrada pelo id:"+turmaId+"!"));
    }

    @Override
    public TurmaResponseDTO updateTurma(String nome, Long turmaId) {

        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new NotFoundException("Turma para edição não encontrada!"));

        boolean existsWithSameNome = turmaRepository.existsByNomeLikeAndIdNot(nome, turmaId);
        if (existsWithSameNome) {
            throw new AlreadyExistsException("Turma com esse Nome já existe");
        }

        turma.setNome(nome);
        turmaRepository.save(turma);

        return new TurmaResponseDTO(turma);
    }

    @Override
    public void deleteTurma(Long turmaId) {
        turmaRepository.findById(turmaId)
                .ifPresentOrElse(turmaRepository::delete,
                        () -> { throw new NotFoundException("Turma para deletar não encontrada");
                });
    }
}
