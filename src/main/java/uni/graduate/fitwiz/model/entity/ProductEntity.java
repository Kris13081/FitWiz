package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uni.graduate.fitwiz.enums.ProductTypeEnum;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductTypeEnum category;

    @NotNull
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    private String sku;

    private boolean inStock;

    @NotEmpty
    private String mainImgPath;

    private String secondImgPath;

    private String thirdImgPath;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<CartEntity> cart;

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
