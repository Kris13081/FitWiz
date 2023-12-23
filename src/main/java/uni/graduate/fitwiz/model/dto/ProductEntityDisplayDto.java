package uni.graduate.fitwiz.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import uni.graduate.fitwiz.enums.ProductTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductEntityDisplayDto {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductTypeEnum category;

    private BigDecimal price;

    private int quantity;

    private String sku;

    private String mainImgPath;

    private String secondImgPath;

    private String thirdImgPath;


}
