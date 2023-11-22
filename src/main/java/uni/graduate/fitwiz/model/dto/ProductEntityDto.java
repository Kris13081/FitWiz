package uni.graduate.fitwiz.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uni.graduate.fitwiz.enums.ProductTypeEnum;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntityDto {

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

    private MultipartFile mainImage;

    private MultipartFile secondImage;

    private MultipartFile thirdImage;

}
