package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import uni.graduate.fitwiz.enums.ProductTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductTypeEnum category;

    @NotEmpty
    private BigDecimal price;

    @NotEmpty
    private int quantity;

    @NotEmpty
    private String mainImgPath;

    private String secondImgPath;

    private String thirdImgPath;


    public ProductEntity() {
    }

    public ProductEntity(
                         String name,
                         String description,
                         ProductTypeEnum category,
                         BigDecimal price,
                         int quantity,
                         String mainImgPath,
                         String secondImgPath,
                         String thirdImgPath
                        ) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.mainImgPath = mainImgPath;
        this.secondImgPath = secondImgPath;
        this.thirdImgPath = thirdImgPath;

    }
}
