package senai.com.backend_atividades.handler.requesthandler.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import senai.com.backend_atividades.domain.user.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthyUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String senha;
    private List<GrantedAuthority> authorities;

    public static AuthyUserDetails buildUserDetails(User user) {

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().getName()));

        return new AuthyUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
