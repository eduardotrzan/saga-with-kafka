package com.order.payment.orch.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.order.payment.orch.dto.enums.PaymentStatusDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "uuid", "createDate", "updateDate", "status", "description", "amount" })
public class PaymentDto {

    private UUID uuid;

    private OffsetDateTime createDate;

    private OffsetDateTime updateDate;

    private PaymentStatusDto status;

    private String description;

    private BigDecimal amount;
}
