package senai.com.backend_atividades.services.user;


import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import senai.com.backend_atividades.domain.Role.Role;
import senai.com.backend_atividades.domain.user.User;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.domain.user.UserRegisterData;
import senai.com.backend_atividades.domain.user.UserResponseData;
import senai.com.backend_atividades.exception.NullListException;

import senai.com.backend_atividades.exception.UserAlreadyExistsException;
import senai.com.backend_atividades.exception.UserNotFoundException;
import senai.com.backend_atividades.repository.RolesRepository;
import senai.com.backend_atividades.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    @Override
    public UserResponseData getUserByid(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Usuario não encontrado!"));

        return new UserResponseData(user);
    }

    @Override
    public List<UserResponseData> getAllUsers() {
        List<UserResponseData> userList = userRepository.findAll().stream().map(UserResponseData::new).toList();
        if(userList.isEmpty()) {
            throw new NullListException("Lista de Usuarios Vazia");
        }
        return userList;
    }

    @Override
    public UserResponseData createUser(UserRegisterDTO request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.email()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.email());
                    user.setName(request.name());
                    user.setPassword(passwordEncoder.encode(request.password()));
                    user.setRole(rolesRepository.findByName("USER").get());
                    userRepository.save(user);
                    return new UserResponseData(user);
                }).orElseThrow(() -> new UserAlreadyExistsException("Oops!" +request.email() +" already exists!"));
    }

    @Override
    public UserResponseData createAdmin(UserRegisterDTO adminRegister) {
        Role adminRole = rolesRepository.findByName("ADMIN").get();
        return Optional.of(adminRegister)
                .filter(user -> !userRepository.existsByEmail(adminRegister.email()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(adminRegister.email());
                    user.setName(adminRegister.name());
                    user.setPassword(passwordEncoder.encode(adminRegister.password()));
                    user.setRole(adminRole);
                    userRepository.save(user);
                    return  new UserResponseData(user);
                }).orElseThrow(() -> new UserAlreadyExistsException("Oops!" +adminRegister.email() +" already exists!"));
    }

    @Override
    public UserResponseData updateUser(User user, Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }


}
