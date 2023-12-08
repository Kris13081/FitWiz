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

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @NotEmpty(message = "Username cannot be empty!")
    @Column(unique = true)
    @Size(min = 2, max = 18)
    private String username;

    @NotEmpty(message = "Password cannot be empty!")
    private String password;

    @NotEmpty(message = "Email cannot be empty!")
    @Email(message = "Must contain '@'.")
    @Column(unique = true)
    private String email;

    private String profileImage;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;


    @OneToOne(fetch = FetchType.LAZY)
    private CartEntity cart;
    public UserEntity() {
        this.roles = new ArrayList<>();
    }
}
