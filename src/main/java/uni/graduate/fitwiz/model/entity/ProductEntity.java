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

    @NotEmpty
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductTypeEnum type;

    public ProductEntity() {
    }

    public ProductEntity(String name, String description, BigDecimal price, ProductTypeEnum type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }
}
