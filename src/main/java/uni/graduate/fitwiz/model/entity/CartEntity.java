package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "carts")
public class CartEntity extends BaseEntity {

    @OneToOne
    @JoinColumn()
    private UserEntity user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<ProductEntity> cartProducts;

    public CartEntity() {
        this.cartProducts = new ArrayList<>();
    }

}
