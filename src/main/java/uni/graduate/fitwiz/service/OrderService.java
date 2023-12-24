package uni.graduate.fitwiz.service;

import uni.graduate.fitwiz.model.dto.OrderDisplayDto;
import uni.graduate.fitwiz.model.dto.OrderEntityDto;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    boolean create(OrderEntityDto orderEntityDto);

    List<OrderDisplayDto> getOrders();

    BigDecimal getOrdersTotalPrice();
}
