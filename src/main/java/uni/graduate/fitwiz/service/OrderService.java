package uni.graduate.fitwiz.service;

import uni.graduate.fitwiz.model.dto.OrderEntityDto;

public interface OrderService {
    boolean create(OrderEntityDto orderEntityDto);
}
