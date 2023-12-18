package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.BannerService;
import uni.graduate.fitwiz.service.BlogService;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping()
public class HomeController {

    private final UserService userService;
    private final BannerService bannerService;
    private final BlogService blogService;

    public HomeController(UserService userService,
                          BannerService bannerService,
                          BlogService blogService) {
        this.userService = userService;
        this.bannerService = bannerService;
        this.blogService = blogService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.displayUserDetails(currentUsername));
    }


    @ModelAttribute(name = "bannersList")
    public void getBanners(Model model) {
        model.addAttribute("bannersList", bannerService.getBanners());
    }

    @ModelAttribute(name = "blogsList")
    public void getBlogs(Model model) {
        model.addAttribute("blogsList", blogService.getBlogs());
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
