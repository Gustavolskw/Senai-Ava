package senai.com.backend_atividades.setup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import senai.com.backend_atividades.domain.Role.Role;
import senai.com.backend_atividades.domain.user.UserRegisterDTO;
import senai.com.backend_atividades.repository.RolesRepository;
import senai.com.backend_atividades.services.user.IUserService;

@Component
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

            UserRegisterDTO userRegister = buildDefaultAdmin();

            iUserService.createUser(userRegister);

        } catch (Exception e) {}


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
