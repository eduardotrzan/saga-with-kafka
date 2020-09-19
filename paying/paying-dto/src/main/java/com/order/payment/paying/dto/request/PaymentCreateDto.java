package com.order.payment.paying.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "description", "amount" })
public class PaymentCreateDto {

    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private UUID orderUuid;
}
