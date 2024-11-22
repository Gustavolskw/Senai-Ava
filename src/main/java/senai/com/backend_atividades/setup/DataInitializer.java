package senai.com.backend_atividades.setup;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import senai.com.backend_atividades.domain.Role.Role;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.repository.RolesRepository;
import senai.com.backend_atividades.services.user.IUserService;

import java.util.List;

@Component
@Service
public class DataInitializer implements CommandLineRunner {


    private final IUserService iUserService;
    private final RolesRepository rolesRepository;

    public DataInitializer(IUserService iUserService, RolesRepository rolesRepository ) {

        this.iUserService = iUserService;
        this.rolesRepository = rolesRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        try {

            createRoles();
            createUser();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Transactional
    public void createRoles() {

        List<Role> defaultRoles = buildDefaultRoles();

        defaultRoles.stream().forEach(role -> {
            rolesRepository.save(role);
        });

    }

    private List<Role> buildDefaultRoles() {
        return List.of(new Role("ADMIN"),
                new Role("TEACHER"),
                new Role("USER"));
    }

    public void createUser() {

        UserRegisterDTO userRegister = buildDefaultAdmin();

        iUserService.createUser(userRegister);

    }

    private UserRegisterDTO buildDefaultAdmin() {
        return new UserRegisterDTO("admin",
                "admin@gmail.com",
                "admin@65468*/62.98+/*52989856*//*/",
                "00000000000",
                null,
                rolesRepository.findById(Long.valueOf(1)).get(),
                null);
    }

}
