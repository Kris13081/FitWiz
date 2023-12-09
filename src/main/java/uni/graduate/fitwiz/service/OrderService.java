package uni.graduate.fitwiz.service;

import uni.graduate.fitwiz.model.dto.OrderEntityDto;
import uni.graduate.fitwiz.model.entity.OrderEntity;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    boolean create(OrderEntityDto orderEntityDto);

    List<OrderEntity> getOrders();

    BigDecimal getOrdersTotalPrice();
}
