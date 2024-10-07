package senai.com.backend_atividades.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.com.backend_atividades.domain.user.User;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.domain.user.UserResponseData;
import senai.com.backend_atividades.exception.UserAlreadyExistsException;
import senai.com.backend_atividades.exception.UserNotFoundException;
import senai.com.backend_atividades.response.ApiResponse;
import senai.com.backend_atividades.services.user.IUserService;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable @Valid Long id) {
        try{
            UserResponseData user = iUserService.getUserByid(id);
            return ResponseEntity.ok().body(new ApiResponse("Sucesso!", user));
        }catch(UserNotFoundException e ){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("add/admin")
    public ResponseEntity<ApiResponse> addAdmin(@Valid @RequestBody UserRegisterDTO user) {
        try{
            iUserService.createAdmin(user);
            return ResponseEntity.ok().body(new ApiResponse("Administrador Registrado com sucesso!", null));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add/user")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody UserRegisterDTO user) {
        try{
            iUserService.createUser(user);
            return ResponseEntity.ok().body(new ApiResponse("Usuario Registrado com Sucesso!", null));
        }catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).body(new ApiResponse(e.getMessage(), null));
        }
    }



    //implementar mais tarde
    public ResponseEntity<ApiResponse> editUser(@Valid @RequestBody UserRegisterDTO user) {
        return ResponseEntity.ok().body(null);
    }






}
