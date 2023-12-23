package uni.graduate.fitwiz.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BannerDisplayDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    @NotEmpty
    private String imagePath;


}
