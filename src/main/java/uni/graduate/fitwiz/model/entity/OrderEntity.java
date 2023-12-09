package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotEmpty
    @Column(name = "delivery_company", nullable = false)
    private String deliveryCompany;

    @NotEmpty
    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @NotEmpty
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotEmpty
    @Column(name = "city", nullable = false)
    private String city;

    @NotEmpty
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(columnDefinition = "Text")
    private String orderedItemsSKUs;

    public OrderEntity() {
    }

    public OrderEntity(String orderCode, UserEntity user,
                       String deliveryCompany, String receiverName,
                       String phoneNumber, String city, String street,
                       BigDecimal total) {
        this.orderCode = orderCode;
        this.user = user;
        this.deliveryCompany = deliveryCompany;
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.total = total;
    }

}
