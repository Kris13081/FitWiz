package uni.graduate.fitwiz.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDisplayDto {

    private Long id;

    private String user;

    private String orderCode;

    @NotEmpty
    private String deliveryCompany;

    @NotEmpty
    private String receiverName;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    private BigDecimal total;

    private String orderedItemsSKUs;

}
