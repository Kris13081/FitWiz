package uni.graduate.fitwiz.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.repository.ProductRepository;
import uni.graduate.fitwiz.service.ProductService;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<String> create(ProductEntityDto productEntityDto) {

        Optional<ProductEntity> optionalProduct = productRepository.findByName(productEntityDto.getName());

        if (optionalProduct.isPresent()) {
            return new ResponseEntity<String>("Product with such a name already exists", HttpStatus.FOUND);
        }
        ProductEntity product = mapDtoToEntity(productEntityDto);
        productRepository.save(product);
        return new ResponseEntity<String>("Successfully created product", HttpStatus.CREATED);
    }

    private ProductEntity mapDtoToEntity(ProductEntityDto productEntityDto) {

        ProductEntity newProduct = new ProductEntity();

        newProduct.setName(productEntityDto.getName());
        newProduct.setDescription(productEntityDto.getDescription());
        newProduct.setCategory(productEntityDto.getCategory());
        newProduct.setPrice(productEntityDto.getPrice());
        newProduct.setQuantity(productEntityDto.getQuantity());

        return newProduct;
    }
}
