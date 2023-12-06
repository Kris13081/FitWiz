package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import uni.graduate.fitwiz.enums.UserRoleEnum;

@Getter
@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "role=" + role +
                '}';
    }
}
