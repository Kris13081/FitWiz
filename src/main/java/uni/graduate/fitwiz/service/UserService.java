package uni.graduate.fitwiz.service;

import uni.graduate.fitwiz.model.dto.UserEntityDto;
import uni.graduate.fitwiz.model.entity.UserEntity;

import java.io.IOException;

public interface UserService {
    boolean create(UserEntityDto userEntityDto) throws IOException;

    UserEntity getUserDetails(String currentUsername);
}
