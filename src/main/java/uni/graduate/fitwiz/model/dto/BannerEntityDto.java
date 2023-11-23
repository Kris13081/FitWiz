package uni.graduate.fitwiz.model.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class BannerEntityDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    private MultipartFile image;


}
