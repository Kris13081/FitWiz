package uni.graduate.fitwiz.service;

import uni.graduate.fitwiz.model.dto.UserEntityDto;
import uni.graduate.fitwiz.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    boolean create(UserEntityDto userEntityDto);

    UserEntity getUserDetails(String currentUsername);
}
