package uni.graduate.fitwiz.model.dto;

import lombok.Getter;
import lombok.Setter;
import uni.graduate.fitwiz.enums.ProductTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateDto {

    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private int quantity;

    private String sku;

}
