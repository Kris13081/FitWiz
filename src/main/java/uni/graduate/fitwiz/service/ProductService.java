package uni.graduate.fitwiz.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.model.dto.ProductUpdateDto;
import uni.graduate.fitwiz.model.entity.ProductEntity;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseEntity<String> create(ProductEntityDto productEntityDto) throws IOException;

    List<ProductEntity> getProducts();

    ProductEntity viewProduct(String sku);

    void deleteProduct(Long id);

    HttpStatus updateProduct(Long id, ProductUpdateDto productUpdateDto);

    List<ProductEntity> getInStockProducts();

    HttpStatus addToCart(String productSKU);
}
