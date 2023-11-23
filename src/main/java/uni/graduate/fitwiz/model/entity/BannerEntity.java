package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "banners")
public class BannerEntity extends BaseEntity{

    @NotEmpty
    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    @NotEmpty
    private String imagePath;

}
