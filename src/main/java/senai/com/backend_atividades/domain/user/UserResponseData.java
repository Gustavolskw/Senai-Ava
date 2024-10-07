package senai.com.backend_atividades.domain.user;

import senai.com.backend_atividades.domain.Role.Role;

import java.util.List;

public record UserResponseData(Long id, String email, String nome, List<String> roles) {
    public UserResponseData(User user ) {
        this(user.getId(), user.getEmail(), user.getNome(), user.getRoles().stream().map(Role::getName).toList());
    }
}
