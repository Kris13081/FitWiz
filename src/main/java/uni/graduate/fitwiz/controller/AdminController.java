package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.BannerService;
import uni.graduate.fitwiz.service.BlogService;
import uni.graduate.fitwiz.service.ProductService;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final UserService userService;
    private final BannerService bannerService;
    private final BlogService blogService;
    private final ProductService productService;

    public AdminController(UserService userService,
                           BannerService bannerService,
                           BlogService blogService,
                           ProductService productService) {
        this.userService = userService;
        this.bannerService = bannerService;
        this.blogService = blogService;
        this.productService = productService;
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

    @ModelAttribute(name = "blogsList")
    public void getBlogs(Model model) {
        model.addAttribute("blogsList", blogService.getBlogs());
    }

    @ModelAttribute(name = "productsList")
    public void getProducts(Model model) {
        model.addAttribute("productsList", productService.getProducts());
    }

    @ModelAttribute(name = "usersList")
    public void getUsers(Model model) {
        model.addAttribute("usersList", userService.getUsers());
    }


    @GetMapping("/index")
    public ModelAndView getAdminMenuPage() {
        return new ModelAndView("/admin/index");
    }

    @GetMapping("/add-product")
    public ModelAndView getAdProductPage() {
        return new ModelAndView("/admin/page-manager/store/add-product");
    }

    @GetMapping("/management/add-banner")
    public ModelAndView getAddBannerPage() {
        return new ModelAndView("/admin/page-manager/home/add-banner");
    }

    @GetMapping("/management/add-blogcard")
    public ModelAndView getAddBlogPage() {
        return new ModelAndView("/admin/page-manager/home/add-blogcard");
    }

    @GetMapping("/all-users")
    public ModelAndView getAllUsersPage() {
        return new ModelAndView("/admin/page-manager/user/all-users");
    }
}
