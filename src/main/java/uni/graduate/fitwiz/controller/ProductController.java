package uni.graduate.fitwiz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.model.dto.ProductUpdateDto;
import uni.graduate.fitwiz.service.ProductService;
import uni.graduate.fitwiz.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService,
                             UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @ModelAttribute(name = "userDetails")
    public void getUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        model.addAttribute("userDetails", userService.displayUserDetails(currentUsername));
    }

    @GetMapping("/products/{sku}")
    public ModelAndView getViewProductPage(@PathVariable String sku) {
        ModelAndView md = new ModelAndView("/admin/page-manager/store/view-product");
        md.addObject("product", productService.viewProduct(sku));
        return md;
    }
    @PostMapping("/admins/add-product")
    public ResponseEntity<String> createProduct(ProductEntityDto productEntityDto) throws IOException {
      return productService.create(productEntityDto);
    }

    @PostMapping("/admins/management/products/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admins/management/products/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto productUpdateDto) {
        HttpStatus status = productService.updateProduct(id, productUpdateDto);

        if (status == HttpStatus.OK) {
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }
}
