package uni.graduate.fitwiz.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.enums.ProductTypeEnum;
import uni.graduate.fitwiz.model.dto.ProductEntityDto;
import uni.graduate.fitwiz.model.dto.ProductUpdateDto;
import uni.graduate.fitwiz.model.entity.ProductEntity;
import uni.graduate.fitwiz.repository.ProductRepository;
import uni.graduate.fitwiz.service.GcsService;
import uni.graduate.fitwiz.service.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
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

    @Override
    public void deleteProduct(Long id) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()){
            ProductEntity product = optionalProduct.get();
            productRepository.delete(product);
        } else {
            throw new IllegalArgumentException("Such product doesn't exist!");
        }
    }

    @Override
    public HttpStatus updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            ProductEntity productEntity = getProductEntity(productUpdateDto, optionalProduct);

            if (productEntity == null) {
                return HttpStatus.BAD_REQUEST;
            }

            productRepository.save(productEntity);
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    private ProductEntity getProductEntity (ProductUpdateDto productUpdateDto, Optional<ProductEntity> optionalProduct){
        ProductEntity productEntity = optionalProduct.get();

        if (!productUpdateDto.getName().isEmpty()) {
            productEntity.setName(productUpdateDto.getName());
        } else {
            return null;
        }

        if (!productUpdateDto.getDescription().isEmpty()) {
            productEntity.setDescription(productUpdateDto.getDescription());
        } else {
            return null;
        }

        if (!productUpdateDto.getCategory().isEmpty()) {
            ProductTypeEnum type = ProductTypeEnum.valueOf(productUpdateDto.getCategory());
            if (type.equals(ProductTypeEnum.FOOD_SUPPLEMENT) ||
                type.equals(ProductTypeEnum.DRINKS) ||
                type.equals(ProductTypeEnum.CLOTHES) ||
                type.equals(ProductTypeEnum.GYM_EQUIPMENT)) {

                productEntity.setCategory(type);
            } else {
                return null;
            }
        }

        if (!productUpdateDto.getPrice().equals(BigDecimal.ZERO) && productUpdateDto.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            productEntity.setPrice(productUpdateDto.getPrice());
        } else {
            return null;
        }

        if (productUpdateDto.getQuantity() >= 0 && productUpdateDto.getQuantity() <= 250) {
            productEntity.setQuantity(productUpdateDto.getQuantity());
        } else {
            return null;
        }

        if (!productUpdateDto.getSku().isEmpty() && productUpdateDto.getSku().length() <= 8) {
            productEntity.setSku(productUpdateDto.getSku());
        } else {
            return null;
        }

        return productEntity;
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
