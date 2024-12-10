package senai.com.backend_atividades.domain.turma;

import jakarta.persistence.*;
import lombok.*;
import senai.com.backend_atividades.domain.DefaultEntity;

import java.time.LocalDate;

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

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "final_date", nullable = false)
    private LocalDate finalDate;

    @Column(name = "img_class")
    private String imgClass;

}
