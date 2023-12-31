package uni.graduate.fitwiz.service;

import org.springframework.http.HttpStatus;
import uni.graduate.fitwiz.model.dto.*;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;

import java.io.IOException;
import java.util.List;

public interface UserService {
    boolean create(UserEntityDto userEntityDto) throws IOException;

    UserDisplayDto displayUserDetails(String currentUsername);

    List<UserDisplayDto> getUsers();

    void delete(Long id);

    HttpStatus updateUser(Long id, UserUpdateDto userUpdateDto);

    List<ProductEntityDisplayDto> getUserCart(String currentUsername);

    UserEntity getCurrentUser();

    List<ProductEntityDisplayDto> mapEntityListToDtoList(List<ProductEntity> entityList);

}
