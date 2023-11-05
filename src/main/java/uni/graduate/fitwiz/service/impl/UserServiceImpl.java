package uni.graduate.fitwiz.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.enums.UserRoleEnum;
import uni.graduate.fitwiz.model.dto.UserEntityDto;
import uni.graduate.fitwiz.model.entity.UserEntity;
import uni.graduate.fitwiz.model.entity.UserRoleEntity;
import uni.graduate.fitwiz.repository.UserRepository;
import uni.graduate.fitwiz.repository.UserRoleRepository;
import uni.graduate.fitwiz.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public boolean create(UserEntityDto userEntityDto) {

        Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(userEntityDto.getEmail());

        if (optionalUser.isPresent()){
            return false;
        }

        UserEntity newUser = dtoToEntityMapper(userEntityDto);
        userRepository.save(newUser);
        return true;
    }

    private UserEntity dtoToEntityMapper(UserEntityDto userEntityDto) {

        UserEntity user = new UserEntity();

        UserRoleEntity role = userRoleRepository.getByRole(UserRoleEnum.USER);
        List<UserRoleEntity> roles = user.getRoles();
        roles.add(role);

        user.setUsername(userEntityDto.getUsername());
        user.setEmail(userEntityDto.getEmail());
        user.setPassword(passwordEncoder.encode(userEntityDto.getPassword()));
        user.setRoles(roles);

        return user;
    }
}