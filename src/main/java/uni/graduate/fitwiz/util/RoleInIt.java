package uni.graduate.fitwiz.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uni.graduate.fitwiz.service.UserRoleService;

@Component
public class RoleInIt implements CommandLineRunner {

    private final UserRoleService roleService;

    public RoleInIt(UserRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initializeRoles();
    }
}
