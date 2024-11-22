package senai.com.backend_atividades.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import senai.com.backend_atividades.domain.DefaultEntity;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
public class Role extends DefaultEntity {

    @Column(name = "role_name")
    private String name;

    public Role(String name) {
        this.name = name;
    }

}

