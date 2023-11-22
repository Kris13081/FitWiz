package uni.graduate.fitwiz.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDto {

    @NotEmpty
    @Column(unique = true)
    @Size(min = 2, max = 18)
    private String username;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    private MultipartFile profileImage;

}
