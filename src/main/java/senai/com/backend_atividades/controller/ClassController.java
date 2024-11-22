package senai.com.backend_atividades.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.com.backend_atividades.domain.turma.ClassRegisterDTO;
import senai.com.backend_atividades.domain.turma.ClassResponseDTO;
import senai.com.backend_atividades.exception.AlreadyExistsException;
import senai.com.backend_atividades.exception.NotFoundException;
import senai.com.backend_atividades.exception.NullListException;
import senai.com.backend_atividades.response.ApiResponse;
import senai.com.backend_atividades.services.clazz.ClassService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addClass(@RequestBody @Valid ClassRegisterDTO turma) {

        try {

            ClassResponseDTO newTurma = classService.createTurma(turma);

            return ResponseEntity.ok().body(new ApiResponse("Turma adicionada Com sucesso!", newTurma));

        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(409).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllClasses() {

        try {

            List<ClassResponseDTO> listTurmas = classService.getTurmas();

            return ResponseEntity.ok().body(new ApiResponse("Sucesso", listTurmas));

        } catch (NullListException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/{classId}/edit")
    public ResponseEntity<ApiResponse> editClass(@PathVariable Long classId, @RequestBody @Valid ClassRegisterDTO turma) {

        try {

            ClassResponseDTO updatedTurma = classService.updateTurma(turma.nome(), classId);

            return ResponseEntity.ok().body(new ApiResponse("Turma editado com sucesso!", updatedTurma));

        } catch (NotFoundException | AlreadyExistsException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/{classId}")
    public ResponseEntity<ApiResponse> deleteClass(@PathVariable Long classId) {

        try {

            classService.deleteTurma(classId);

            return ResponseEntity.ok().body(new ApiResponse("Turma deletada com sucesso!", null));

        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/{turmaId}/turma")
    public ResponseEntity<ApiResponse> getTurmaById(@PathVariable Long turmaId) {

        try {
            return ResponseEntity.ok().body(new ApiResponse("Scuesso", classService.getTurmaById(turmaId)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }

    }

}
