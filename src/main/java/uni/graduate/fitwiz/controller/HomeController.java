package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping()
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.getUserDetails(currentUsername));
    }

    @GetMapping("/api/home")
    public ModelAndView homePage() {
        return new ModelAndView("home");
    }


    @GetMapping("/error-continue")
    public ModelAndView home() {
        return new ModelAndView("home");
    }
}
