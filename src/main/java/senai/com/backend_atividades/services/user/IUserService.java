package senai.com.backend_atividades.services.user;

import org.springframework.web.multipart.MultipartFile;
import senai.com.backend_atividades.domain.Role.Role;
import senai.com.backend_atividades.domain.user.User;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.domain.user.UserResponseData;

import java.util.List;

public interface IUserService {


    UserResponseData getUserByid(Long id);

    List<UserResponseData> getAllUsers();

    UserResponseData createUser(UserRegisterDTO user);

    UserResponseData updateUser(UserRegisterDTO user, Long id);

    void deleteUser(Long id);


}
