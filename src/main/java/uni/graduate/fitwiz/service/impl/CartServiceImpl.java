package uni.graduate.fitwiz.service.impl;

import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.entity.CartEntity;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;
import uni.graduate.fitwiz.repository.CartRepository;
import uni.graduate.fitwiz.service.CartService;
import uni.graduate.fitwiz.service.UserService;

import java.util.List;

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

    @Override
    public CartEntity getCart(UserEntity currentUser) {
       return cartRepository.findByUser(currentUser);
    }

    @Override
    public void addProduct(CartEntity userCart, ProductEntity product) {
        CartEntity cart = getCart(userCart.getUser());

        List<ProductEntity> cartProducts = cart.getCartProducts();

        cartProducts.add(product);
        cart.setCartProducts(cartProducts);
        cartRepository.save(cart);
    }


    private CartEntity create(UserEntity user) {
        CartEntity cart = new CartEntity();
        cart.setUser(user);
        cartRepository.save(cart);
        return cart;
    }
}
