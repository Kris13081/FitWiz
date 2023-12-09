package uni.graduate.fitwiz.service;

import uni.graduate.fitwiz.model.entity.CartEntity;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;

public interface CartService {


    CartEntity createCart(UserEntity newUser);


    CartEntity getCart(UserEntity currentUser);

    void addProduct(CartEntity userCart, ProductEntity product);

    void removeProduct(ProductEntity product, CartEntity cart);
}
