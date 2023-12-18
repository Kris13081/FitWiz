package uni.graduate.fitwiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.*;
import uni.graduate.fitwiz.util.logging.service.LoggingService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final UserService userService;
    private final BannerService bannerService;
    private final BlogService blogService;
    private final ProductService productService;
    private final LoggingService loggingService;
    private final OrderService orderService;

    public AdminController(UserService userService,
                           BannerService bannerService,
                           BlogService blogService,
                           ProductService productService,
                           LoggingService loggingService,
                           OrderService orderService) {
        this.userService = userService;
        this.bannerService = bannerService;
        this.blogService = blogService;
        this.productService = productService;
        this.loggingService = loggingService;
        this.orderService = orderService;
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

    @ModelAttribute(name = "ordersList")
    public void getOrders(Model model) {
        model.addAttribute("ordersList", orderService.getOrders());
    }

    @ModelAttribute(name = "ordersTotalPrice")
    public void getTotalPrice(Model model) {
        model.addAttribute("ordersTotalPrice", orderService.getOrdersTotalPrice());
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

    @ModelAttribute(name = "logsList")
    public void getLogs(Model model) {
        model.addAttribute("logsList", loggingService.getLogs());
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

    @GetMapping("/all-orders")
    public ModelAndView getAllOrdersPage() {
        return new ModelAndView("/admin/page-manager/store/all-orders");
    }

    @GetMapping("/management/homepage-manager")
    public ModelAndView getHomepageManagerPage() {
        return new ModelAndView("/admin/page-manager/home/homepage-manager");
    }

    @GetMapping("/management/all-products")
    public ModelAndView getAllProductsPage() {
        return new ModelAndView("/admin/page-manager/store/all-products");
    }
}
