package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.*;
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

    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ProductEntity> cartProducts;

    public CartEntity() {
        this.cartProducts = new ArrayList<>();
    }

}
