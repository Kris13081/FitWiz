package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.getUserDetails(currentUsername));
    }

    @GetMapping("/index")
    public ModelAndView getAdminMenuPage() {
        return new ModelAndView("/admin/index");
    }

    @GetMapping("/add-product")
    public ModelAndView getAdProductPage() {
        return new ModelAndView("/admin/add-product");
    }

    @GetMapping("/management/add-banner")
    public ModelAndView getAddBannerPage() {
        return new ModelAndView("/admin/page-manager/add-banner");
    }

    @GetMapping("/management/add-blogcard")
    public ModelAndView getHomePageManagerPage() {
        return new ModelAndView("/admin/page-manager/add-blogcard");
    }
}
