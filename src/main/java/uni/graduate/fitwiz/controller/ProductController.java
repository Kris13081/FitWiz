package uni.graduate.fitwiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.service.ProductService;

import java.io.IOException;

@RestController
@RequestMapping("/api/admins")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> createProduct(ProductEntityDto productEntityDto) throws IOException {
      return productService.create(productEntityDto);
    }
}
