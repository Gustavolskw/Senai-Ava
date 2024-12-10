package senai.com.backend_atividades.services.user;


import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
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
import senai.com.backend_atividades.exception.Validation;
import senai.com.backend_atividades.repository.UserRepository;
import senai.com.backend_atividades.util.CPFCNPJValidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseData getUserByid(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado!"));

        return new UserResponseData(user);

    }

    @Override
    public List<UserResponseData> getAllUsers() {

        List<UserResponseData> userList = userRepository.findAll().stream().map(UserResponseData::new).toList();

        if (userList.isEmpty()) {
            throw new NullListException("Lista de Usuarios Vazia");
        }

        return userList;

    }

    @Override
    public UserResponseData createUser(UserRegisterDTO request) {

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {

                    validateMandatoryFields(request);

                    User user = buildUser(request);

                    userRepository.save(user);

                    saveImage(request.getImage(), user);

                    return new UserResponseData(user);

                })
                .orElseThrow(() -> new UserAlreadyExistsException("Oops! User already exists!"));

    }

    @Override
    public UserResponseData updateUser(UserRegisterDTO request, Long id) {

        validateMandatoryFields(request);

        return Optional.ofNullable(userRepository.findById(id))
                .get()
                .map((userDb) -> {

                    validateEmailUpdate(userDb, userDb);

                    updateData(request, userDb);

                    userRepository.save(userDb);

                    return new UserResponseData(userDb);

                })
                .orElseThrow(() -> new UserNotFoundException("Usuario não existe!"));

    }

    private void validateEmailUpdate(User userEdit, User userDb) {

        if (!userEdit.getEmail().equals(userDb.getEmail()) && userRepository.existsByEmail(userEdit.getEmail())) {
            new Validation().add("Email", "Já existe um usuário com este email").throwIfHasErrors();
        }

    }

    @Transactional
    public void validateMandatoryFields(UserRegisterDTO user) {

        Validation validation = new Validation();

        if (StringUtils.isBlank(user.getName())) {
            validation.add("Nome", "Informe o nome");
        }

        if (StringUtils.isBlank(user.getEmail())) {
            validation.add("Email", "Informe o email");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            validation.add("Senha", "Informe a senha");
        }

        if (user.getRole() == null) {
            validation.add("Permissão", "Informe o nível de permissão do usuário");
        }

        if (StringUtils.isEmpty(user.getCpf()) || !CPFCNPJValidator.isValidCpf(user.getCpf())) {

            if (!isAdm(user)) {
                validation.add("CPF", "Informe um CPF válido");
            }

        }

        validation.throwIfHasErrors();

    }

    @Override
    public void deleteUser(Long id) {

    }

    private void updateData(UserRegisterDTO user, User userDb) {

        userDb.setEmail(user.getEmail());
        userDb.setName(user.getName());
        userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userDb.setRole(user.getRole());
        userDb.setCpf(user.getCpf());

    }

    private User buildUser(UserRegisterDTO request) {

        User user = new User();

        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setCpf(request.getCpf());

        return user;

    }

    private Path getFilePath(User user, MultipartFile image) {

        String uniqueFileName = String.valueOf(user.getId()) + "." + FilenameUtils.getExtension(image.getOriginalFilename());

        return Path.of("src/main/resources/img/");

    }

    public void saveImage(MultipartFile image, User user) {

        try {

            if (image != null) {

                Path uploadPath = Path.of("src/main/resources/img/");
                Path filePath = getFilePath(user, image);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public Boolean isAdm(UserRegisterDTO userRegisterDTO) {
        return userRegisterDTO.getRole() != null && userRegisterDTO.getRole().getId() == 1;
    }

}
