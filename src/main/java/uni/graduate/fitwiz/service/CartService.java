package uni.graduate.fitwiz.service;

import uni.graduate.fitwiz.model.entity.CartEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;

public interface CartService {


    CartEntity createCart(UserEntity newUser);
}
