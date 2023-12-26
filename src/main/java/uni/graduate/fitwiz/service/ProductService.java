package uni.graduate.fitwiz.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.ProductDisplayDto;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.model.dto.ProductUpdateDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseEntity<String> create(ProductEntityDto productEntityDto) throws IOException;

    List<ProductDisplayDto> getProducts();

    ProductDisplayDto viewProduct(String sku);

    void deleteProduct(Long id);

    HttpStatus updateProduct(Long id, ProductUpdateDto productUpdateDto);

    List<ProductDisplayDto> getInStockProducts();

    HttpStatus addToCart(String productSKU);

    void reduceQuantity(String productSku);

}
