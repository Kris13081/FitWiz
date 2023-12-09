package uni.graduate.fitwiz.service.impl;

import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.dto.OrderEntityDto;
import uni.graduate.fitwiz.model.entity.CartEntity;
import uni.graduate.fitwiz.model.entity.OrderEntity;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;
import uni.graduate.fitwiz.repository.OrderRepository;
import uni.graduate.fitwiz.service.CartService;
import uni.graduate.fitwiz.service.OrderService;
import uni.graduate.fitwiz.service.ProductService;
import uni.graduate.fitwiz.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }


    @Override
    public boolean create(OrderEntityDto orderEntityDto) {

        UserEntity user = userService.getCurrentUser();
        CartEntity cart = cartService.getCart(user);

        List<ProductEntity> orderedProducts = cart.getCartProducts();
        StringBuilder receipt = new StringBuilder();
        
        if (orderedProducts.isEmpty()) {
            return false;
        }

        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        for (ProductEntity product : orderedProducts) {
            totalOrderPrice = totalOrderPrice.add(product.getPrice());
            receipt.append("| ").append(product.getName()).append("-SKU:").append(product.getSku()).append(" |, ").append("\n");

            productService.reduceQuantity(product.getSku());
            cartService.removeProduct(product, cart);
        }

        OrderEntity order = mapDtoToEntity(orderEntityDto);

        order.setUser(user);
        order.setOrderedItemsSKUs(receipt.toString());
        order.setTotal(totalOrderPrice);

        orderRepository.save(order);

        return true;
    }

    @Override
    public List<OrderEntity> getOrders() {

        return orderRepository.findAll();
    }

    @Override
    public BigDecimal getOrdersTotalPrice() {
        List<OrderEntity> orders = getOrders();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderEntity order : orders) {
            totalPrice = totalPrice.add(order.getTotal());
        }
        return totalPrice;
    }

    private OrderEntity mapDtoToEntity(OrderEntityDto orderEntityDto) {
        OrderEntity newOrder = new OrderEntity();

        newOrder.setOrderCode(String.valueOf(UUID.randomUUID()).substring(0,8));
        newOrder.setDeliveryCompany(orderEntityDto.getDeliveryCompany());
        newOrder.setReceiverName(orderEntityDto.getReceiverName());
        newOrder.setPhoneNumber(orderEntityDto.getPhoneNumber());
        newOrder.setCity(orderEntityDto.getCity());
        newOrder.setStreet(orderEntityDto.getStreet());

        return newOrder;
    }
}
