package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @NotEmpty
    @Column(unique = true)
    @Size(min = 2, max = 18)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    @Email
    private String email;

    private String profileImage;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles = new ArrayList<>();

}
