package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.model.dto.OrderEntityDto;
import uni.graduate.fitwiz.service.OrderService;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping("/api/users")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService,
                           UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.displayUserDetails(currentUsername));
    }

    @GetMapping("/order/finished")
    private ModelAndView getOrderFinishedPage() {
        return new ModelAndView("/admin/page-manager/store/orderCompleted");
    }

    @PostMapping("/cart/order")
    public ModelAndView creatOrder(OrderEntityDto orderEntityDto) {
        boolean created = orderService.create(orderEntityDto);

        return created ? new ModelAndView("redirect:/api/users/order/finished") :  new ModelAndView("redirect:/api/users/cart");
    }
}
