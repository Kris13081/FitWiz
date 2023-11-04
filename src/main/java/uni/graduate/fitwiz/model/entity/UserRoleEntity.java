package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.*;
import uni.graduate.fitwiz.enums.UserRoleEnum;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public UserRoleEnum getRole() {
        return role;
    }

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
