package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.BannerService;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping()
public class HomeController {

    private final UserService userService;
    private final BannerService bannerService;

    public HomeController(UserService userService,
                          BannerService bannerService) {
        this.userService = userService;
        this.bannerService = bannerService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.getUserDetails(currentUsername));
    }

    @ModelAttribute(name = "bannersList")
    public void getBanners(Model model) {
        model.addAttribute("bannersList", bannerService.getBanners());
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
