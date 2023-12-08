package uni.graduate.fitwiz.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.repository.ProductRepository;
import uni.graduate.fitwiz.service.GcsService;
import uni.graduate.fitwiz.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final GcsService gcsService;

    public ProductServiceImpl(ProductRepository productRepository, GcsService gcsService) {
        this.productRepository = productRepository;
        this.gcsService = gcsService;
    }

    @Override
    public ResponseEntity<String> create(ProductEntityDto productEntityDto) throws IOException {

        Optional<ProductEntity> optionalProduct = productRepository.findByName(productEntityDto.getName());

        if (optionalProduct.isPresent()) {
            return new ResponseEntity<>("Product with such a name already exists", HttpStatus.FOUND);
        }
        ProductEntity product = mapDtoToEntity(productEntityDto);
        productRepository.save(product);
        return new ResponseEntity<>("Successfully created product", HttpStatus.CREATED);
    }

    @Override
    public List<ProductEntity> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity viewProduct(String sku) {
        Optional<ProductEntity> optionalProduct = productRepository.findBySku(sku);

        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new IllegalArgumentException("User dont exist;");
        }
    }

    private ProductEntity mapDtoToEntity(ProductEntityDto productEntityDto) throws IOException {

        ProductEntity newProduct = new ProductEntity();

        String mainImagePath = gcsService.uploadProductImage("fitwiz_images_bucket", productEntityDto.getMainImage());
        String secondImagePath = gcsService.uploadProductImage("fitwiz_images_bucket", productEntityDto.getSecondImage());
        String thirdImagePath = gcsService.uploadProductImage("fitwiz_images_bucket", productEntityDto.getThirdImage());

        newProduct.setMainImgPath(mainImagePath);
        newProduct.setSecondImgPath(secondImagePath);
        newProduct.setThirdImgPath(thirdImagePath);

        newProduct.setName(productEntityDto.getName());
        newProduct.setDescription(productEntityDto.getDescription());
        newProduct.setCategory(productEntityDto.getCategory());
        newProduct.setPrice(productEntityDto.getPrice());
        newProduct.setQuantity(productEntityDto.getQuantity());
        newProduct.setSku(UUID.randomUUID().toString().substring(0, 8));

        return newProduct;
    }
}
