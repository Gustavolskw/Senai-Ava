package senai.com.backend_atividades.domain.turma;

import jakarta.persistence.*;
import lombok.*;
import senai.com.backend_atividades.domain.DefaultEntity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class")
public class Class extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}
