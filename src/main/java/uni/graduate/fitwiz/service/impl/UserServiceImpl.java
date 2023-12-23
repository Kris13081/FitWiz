package uni.graduate.fitwiz.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.enums.UserRoleEnum;
import uni.graduate.fitwiz.model.dto.ProductEntityDisplayDto;
import uni.graduate.fitwiz.model.dto.UserDisplayDto;
import uni.graduate.fitwiz.model.dto.UserEntityDto;
import uni.graduate.fitwiz.model.dto.UserUpdateDto;
import uni.graduate.fitwiz.model.entity.CartEntity;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;
import uni.graduate.fitwiz.model.entity.UserRoleEntity;
import uni.graduate.fitwiz.repository.UserRepository;
import uni.graduate.fitwiz.repository.UserRoleRepository;
import uni.graduate.fitwiz.service.CartService;
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
    private final CartService cartService;
    private final GcsService gcsService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository,
                           CartService cartService,
                           GcsService gcsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.cartService = cartService;
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
        CartEntity cart = cartService.createCart(newUser);
        cart.setUser(newUser);
        newUser.setCart(cart);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public UserDisplayDto displayUserDetails(String currentUsername) {

        Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(currentUsername);

        if (optionalUser.isPresent()) {
            return entityToDto(userRepository.getUserEntitiesByEmail(currentUsername));
        }
        return null;
    }


    @Override
    public List<UserDisplayDto> getUsers() {
        List<UserEntity> entitiesList = userRepository.findAll();
        List<UserDisplayDto> dtoList = new ArrayList<>();

        for (UserEntity userEntity : entitiesList) {
            dtoList.add(entityToDto(userEntity));
        }
        return dtoList;
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

    @Override
    public List<ProductEntityDisplayDto> getUserCart(String currentUsername) {
        Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(currentUsername);

        UserEntity user = optionalUser.orElse(null);

        CartEntity cart = cartService.getCart(user);

        List<ProductEntity> cartProducts = cart.getCartProducts();

        return mapEntityListToDtoList(cartProducts);
    }

    @Override
    public List<ProductEntityDisplayDto> mapEntityListToDtoList(List<ProductEntity> entityList) {
        List<ProductEntityDisplayDto> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(mapEntityToDto(entity)));

        return dtoList;
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {

            // Retrieve the user based on the username (email in your case)
            Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(userDetails.getUsername());

            return optionalUser.orElse(null);
        }

        return null;
    }

    private UserEntity getUserDetails(String currentUsername) {

        Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(currentUsername);

        if (optionalUser.isPresent()) {
            return userRepository.getUserEntitiesByEmail(currentUsername);
        }
        return null;
    }

    private UserEntity getUserEntity(UserUpdateDto userUpdateDto, Optional<UserEntity> optionalUser) {
        UserEntity userEntity = optionalUser.orElse(null);

        if (!userUpdateDto.getUsername().isEmpty()) {
            if (userEntity != null) {
                userEntity.setUsername(userUpdateDto.getUsername());
            }
        } else {
            return null;
        }

        if (!userUpdateDto.getEmail().isEmpty() && userUpdateDto.getEmail().contains("@") && userUpdateDto.getEmail().contains(".")) {
            if (userEntity != null) {
                userEntity.setEmail(userUpdateDto.getEmail());
            }
        } else {
            return null;
        }

        if (!userUpdateDto.getRole().isEmpty() && userUpdateDto.getRole().equals("ADMIN")) {
            UserRoleEntity admin = userRoleRepository.getByRole(UserRoleEnum.ADMIN);
            UserRoleEntity user = userRoleRepository.getByRole(UserRoleEnum.USER);
            List<UserRoleEntity> roles = new ArrayList<>();

            roles.add(admin);
            roles.add(user);

            if (userEntity != null) {
                userEntity.setRoles(roles);
            }
        } else {
            UserRoleEntity user = userRoleRepository.getByRole(UserRoleEnum.USER);
            List<UserRoleEntity> roles = new ArrayList<>();

            roles.add(user);

            if (userEntity != null) {
                userEntity.setRoles(roles);
            }
        }

        return userEntity;
    }

    private UserEntity dtoToEntityMapper(UserEntityDto userEntityDto) throws IOException {
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

    private UserDisplayDto entityToDto(UserEntity userEntity) {
        UserDisplayDto dto = new UserDisplayDto();

        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        dto.setProfileImage(userEntity.getProfileImage());
        dto.setRoles(userEntity.getRoles());

        return dto;
    }

    private ProductEntityDisplayDto mapEntityToDto(ProductEntity entity) {
        ProductEntityDisplayDto dto = new ProductEntityDisplayDto();

        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setQuantity(entity.getQuantity());
        dto.setDescription(entity.getDescription());
        dto.setMainImgPath(entity.getMainImgPath());
        dto.setSecondImgPath(entity.getSecondImgPath());
        dto.setThirdImgPath(entity.getThirdImgPath());
        dto.setSku(entity.getSku());

        return dto;
    }
}