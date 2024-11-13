package senai.com.backend_atividades.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import senai.com.backend_atividades.domain.Role.Roles;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.domain.user.UserResponseData;
import senai.com.backend_atividades.exception.UserAlreadyExistsException;
import senai.com.backend_atividades.exception.UserNotFoundException;
import senai.com.backend_atividades.repository.RolesRepository;
import senai.com.backend_atividades.response.ApiResponse;
import senai.com.backend_atividades.services.user.IUserService;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;
    private final RolesRepository rolesRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable @Valid Long id) {

        try {

            UserResponseData user = iUserService.getUserByid(id);

            return ResponseEntity.ok().body(new ApiResponse("Sucesso!", user));

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @Secured({"ADMIN", "TEACHER"})
    @PostMapping("add/{role}")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody UserRegisterDTO user, @PathVariable("role") Roles role) {

        try {

            iUserService.createUser(user, rolesRepository.findById(role.getValue()).get());

            return ResponseEntity.ok().body(new ApiResponse(role.getDescription() + " Registrado com sucesso!", null));

        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).body(new ApiResponse(e.getMessage(), null));
        }

    }


}
