package uni.graduate.fitwiz.service;

import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;

public interface ProductService {

    ResponseEntity<String> create(ProductEntityDto productEntityDto);
}
