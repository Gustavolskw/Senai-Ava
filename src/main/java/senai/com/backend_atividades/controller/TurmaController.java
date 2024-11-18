package senai.com.backend_atividades.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.com.backend_atividades.domain.turma.TurmaRegisterDTO;
import senai.com.backend_atividades.domain.turma.TurmaResponseDTO;
import senai.com.backend_atividades.exception.AlreadyExistsException;
import senai.com.backend_atividades.exception.NotFoundException;
import senai.com.backend_atividades.exception.NullListException;
import senai.com.backend_atividades.response.ApiResponse;
import senai.com.backend_atividades.services.turma.TurmaService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/turma")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService turmaService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> adicionaTurma(@RequestBody @Valid TurmaRegisterDTO turma) {
        try {
            TurmaResponseDTO newTurma = turmaService.createTurma(turma);
            return  ResponseEntity.ok().body(new ApiResponse("Turma adicionada Com sucesso!", newTurma));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(409).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllTurmas(){
        try{
            List<TurmaResponseDTO> listTurmas = turmaService.getTurmas();
            return ResponseEntity.ok().body(new ApiResponse("Sucesso",listTurmas));
        }catch (NullListException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{turmaId}/edit")
    public ResponseEntity<ApiResponse> editTurma(@PathVariable Long turmaId, @RequestBody @Valid TurmaRegisterDTO turma) {
        try{
            TurmaResponseDTO updatedTurma = turmaService.updateTurma(turma.nome(), turmaId);
            return ResponseEntity.ok().body(new ApiResponse("Turma editado com sucesso!", updatedTurma));
        } catch (NotFoundException|AlreadyExistsException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));

        }
    }

    @DeleteMapping("/delete/{turmaId}")
    public ResponseEntity<ApiResponse> deleteTurma(@PathVariable Long turmaId) {
        try{
            turmaService.deleteTurma(turmaId);
            return ResponseEntity.ok().body(new ApiResponse("Turma deletada com sucesso!", null));
        }catch(NotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/{turmaId}/turma")
    public ResponseEntity<ApiResponse> getTurmaById(@PathVariable Long turmaId) {
        try{
            return ResponseEntity.ok().body(new ApiResponse("Scuesso", turmaService.getTurmaById(turmaId)));
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
