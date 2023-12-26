package uni.graduate.fitwiz.model.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uni.graduate.fitwiz.enums.ProductTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDisplayDto {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty

    private String description;

    private ProductTypeEnum category;

    @NotNull
    private BigDecimal price;


    private int quantity;

    private String sku;

    private boolean inStock;

    @NotEmpty
    private String mainImgPath;

    private String secondImgPath;

    private String thirdImgPath;


}
