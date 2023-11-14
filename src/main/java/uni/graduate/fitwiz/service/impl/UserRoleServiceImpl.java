package uni.graduate.fitwiz.service.impl;

import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.enums.UserRoleEnum;
import uni.graduate.fitwiz.model.entity.UserRoleEntity;
import uni.graduate.fitwiz.repository.UserRoleRepository;
import uni.graduate.fitwiz.service.UserRoleService;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository roleRepository;

    public UserRoleServiceImpl(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initializeRoles() {
        List<UserRoleEntity> all = roleRepository.findAll();

        if (all.isEmpty()) {
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRole(UserRoleEnum.USER);

            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIN);

            roleRepository.save(userRole);
            roleRepository.save(adminRole);

        } else if (all.contains(roleRepository.getByRole(UserRoleEnum.USER))) {

            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIN);
            roleRepository.save(adminRole);
        } else if(all.contains(roleRepository.getByRole(UserRoleEnum.ADMIN))){

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRole(UserRoleEnum.USER);
            roleRepository.save(userRole);
        }
    }

}
