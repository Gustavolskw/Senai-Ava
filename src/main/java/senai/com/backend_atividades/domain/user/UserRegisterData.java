package senai.com.backend_atividades.domain.user;

import lombok.Data;

@Data
public class UserRegisterData {
    private String name;
    private String email;
    private String senha;
}
