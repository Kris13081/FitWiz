package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.UserService;

@Controller
@RequestMapping("/api/users")
public class CalculatorController {

    private final UserService userService;

    public CalculatorController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.displayUserDetails(currentUsername));
    }

    @GetMapping("/calculator")
    public ModelAndView getCalculatorPage() {
        return new ModelAndView("/calculator");
    }

}
