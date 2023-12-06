package uni.graduate.fitwiz.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.enums.UserRoleEnum;
import uni.graduate.fitwiz.model.dto.UserEntityDto;
import uni.graduate.fitwiz.model.dto.UserUpdateDto;
import uni.graduate.fitwiz.model.entity.UserEntity;
import uni.graduate.fitwiz.model.entity.UserRoleEntity;
import uni.graduate.fitwiz.repository.UserRepository;
import uni.graduate.fitwiz.repository.UserRoleRepository;
import uni.graduate.fitwiz.service.GcsService;
import uni.graduate.fitwiz.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final GcsService gcsService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository,
                           GcsService gcsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.gcsService = gcsService;
    }

    @Override
    public boolean create(UserEntityDto userEntityDto) throws IOException {

        Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(userEntityDto.getEmail());

        if (optionalUser.isPresent()) {
            return false;
        }


        UserEntity newUser = dtoToEntityMapper(userEntityDto);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public UserEntity getUserDetails(String currentUsername) {

        Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(currentUsername);

        if (optionalUser.isPresent()) {
            return userRepository.getUserEntitiesByEmail(currentUsername);
        }
        return null;
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
    }

    @Override
    public HttpStatus updateUser(Long id, UserUpdateDto userUpdateDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity userEntity = getUserEntity(userUpdateDto, optionalUser);

            if (userEntity == null) {
                return HttpStatus.BAD_REQUEST;
            }

            userRepository.save(userEntity);
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

        private UserEntity getUserEntity (UserUpdateDto userUpdateDto, Optional <UserEntity> optionalUser){
            UserEntity userEntity = optionalUser.get();

            if (!userUpdateDto.getUsername().isEmpty()) {
                userEntity.setUsername(userUpdateDto.getUsername());
            } else {
                return null;
            }

            if (!userUpdateDto.getEmail().isEmpty() && userUpdateDto.getEmail().contains("@") && userUpdateDto.getEmail().contains(".")) {
                userEntity.setEmail(userUpdateDto.getEmail());
            } else {
                return null;
            }

            if (!userUpdateDto.getRole().isEmpty() && userUpdateDto.getRole().equals("ADMIN")){
                UserRoleEntity admin = userRoleRepository.getByRole(UserRoleEnum.ADMIN);
                UserRoleEntity user = userRoleRepository.getByRole(UserRoleEnum.USER);
                List<UserRoleEntity> roles = new ArrayList<>();

                roles.add(admin);
                roles.add(user);

                userEntity.setRoles(roles);
            } else {
                UserRoleEntity user = userRoleRepository.getByRole(UserRoleEnum.USER);
                List<UserRoleEntity> roles = new ArrayList<>();

                roles.add(user);

                userEntity.setRoles(roles);
            }

            return userEntity;
        }

        private UserEntity dtoToEntityMapper (UserEntityDto userEntityDto) throws IOException {
            UserEntity user = getUserDetails(userEntityDto.getUsername());

            if (user == null) {
                user = new UserEntity();

                String profileImagePath = gcsService.uploadProfileImages("fitwiz_images_bucket", userEntityDto.getProfileImage());
                UserRoleEntity role = userRoleRepository.getByRole(UserRoleEnum.USER);
                List<UserRoleEntity> roles = user.getRoles();
                roles.add(role);

                user.setUsername(userEntityDto.getUsername());
                user.setEmail(userEntityDto.getEmail());
                user.setPassword(passwordEncoder.encode(userEntityDto.getPassword()));
                user.setProfileImage(profileImagePath);
                user.setRoles(roles);
                userRepository.save(user);
            }

            return user;
        }

    }