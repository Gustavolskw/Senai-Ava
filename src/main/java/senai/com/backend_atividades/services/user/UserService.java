package senai.com.backend_atividades.services.user;


import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import senai.com.backend_atividades.domain.Role.Role;
import senai.com.backend_atividades.domain.user.User;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.domain.user.UserResponseData;
import senai.com.backend_atividades.exception.NullListException;
import senai.com.backend_atividades.exception.UserAlreadyExistsException;
import senai.com.backend_atividades.exception.UserNotFoundException;
import senai.com.backend_atividades.repository.RolesRepository;
import senai.com.backend_atividades.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

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
    public UserResponseData createUser(UserRegisterDTO request, Role role, MultipartFile image) {

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.email()))
                .map(req -> {

                    User user = buildUser(request, role);

                    userRepository.save(user);

                    saveImage(image, user);

                    return new UserResponseData(user);

                })
                .orElseThrow(() -> new UserAlreadyExistsException("Oops! User already exists!"));

    }

    public void saveImage(MultipartFile image, User user) {

        try {

            String uniqueFileName = String.valueOf(user.getId()) + "." + FilenameUtils.getExtension(image.getOriginalFilename());

            Path uploadPath = Path.of("src/main/resources/img/");

            Path filePath = uploadPath.resolve(uniqueFileName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private User buildUser(UserRegisterDTO request, Role role) {

        User user = new User();

        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);

        return user;

    }

    @Override
    public UserResponseData updateUser(User user, Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }


}
