package uni.graduate.fitwiz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.service.ProductService;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping("/api/store")
public class StoreController {
    private final UserService userService;
    private final ProductService productService;

    public StoreController(UserService userService,
                           ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.displayUserDetails(currentUsername));
    }

    @ModelAttribute(name = "productsList")
    public void getProducts(Model model) {
        model.addAttribute("productsList", productService.getProducts());
    }

    @ModelAttribute(name = "inStockProducts")
    public void getInStockProducts(Model model) {
        model.addAttribute("inStockProducts", productService.getInStockProducts());
    }

    @GetMapping
    public ModelAndView getStorePage() {
        return new ModelAndView("store");
    }


    @PostMapping("/cart/add/{productSKU}")
    public HttpStatus addProductToCart(@PathVariable String productSKU) {
       return productService.addToCart(productSKU);
    }
}
