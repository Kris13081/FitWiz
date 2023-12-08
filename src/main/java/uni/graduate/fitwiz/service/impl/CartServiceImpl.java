package uni.graduate.fitwiz.service.impl;

import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.entity.CartEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;
import uni.graduate.fitwiz.repository.CartRepository;
import uni.graduate.fitwiz.service.CartService;
import uni.graduate.fitwiz.service.UserService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartEntity createCart(UserEntity newUser) {
        return create(newUser);
    }

    private CartEntity create(UserEntity user) {
        CartEntity cart = new CartEntity();
        cart.setUser(user);
        cartRepository.save(cart);
        return cart;
    }
}
