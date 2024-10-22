package senai.com.backend_atividades.domain.Role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import senai.com.backend_atividades.domain.user.User;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "role_name")
    private String name;

}

