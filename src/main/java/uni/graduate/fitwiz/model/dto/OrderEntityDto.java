package uni.graduate.fitwiz.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntityDto {

    @NotBlank
    @Column(name = "delivery_company", nullable = false)
    private String deliveryCompany;

    @NotBlank
    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "street", nullable = false)
    private String street;

}
