package uni.graduate.fitwiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.model.dto.OrderEntityDto;
import uni.graduate.fitwiz.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/users/cart/order")
    public ModelAndView creatOrder(OrderEntityDto orderEntityDto) {
        boolean created = orderService.create(orderEntityDto);

        return created ? new ModelAndView("redirect:/api/home") :  new ModelAndView("redirect:/api/users/cart");
    }
}
