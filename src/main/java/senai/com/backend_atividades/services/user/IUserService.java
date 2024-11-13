package senai.com.backend_atividades.services.user;

import senai.com.backend_atividades.domain.Role.Role;
import senai.com.backend_atividades.domain.user.User;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.domain.user.UserResponseData;

import java.util.List;

public interface IUserService {


    UserResponseData getUserByid(Long id);

    List<UserResponseData> getAllUsers();

    UserResponseData createUser(UserRegisterDTO user, Role role);

    UserResponseData updateUser(User user, Long id);

    void deleteUser(Long id);


}
