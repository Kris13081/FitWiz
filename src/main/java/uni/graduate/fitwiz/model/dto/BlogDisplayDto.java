package uni.graduate.fitwiz.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDisplayDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String url;

    @NotEmpty
    private String imagePath;


}
