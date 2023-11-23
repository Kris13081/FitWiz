package uni.graduate.fitwiz.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BannerEntityDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    private MultipartFile image;


}
