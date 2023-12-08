package uni.graduate.fitwiz.service;

import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.model.entity.ProductEntity;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseEntity<String> create(ProductEntityDto productEntityDto) throws IOException;

    List<ProductEntity> getProducts();

    ProductEntity viewProduct(String sku);
}
