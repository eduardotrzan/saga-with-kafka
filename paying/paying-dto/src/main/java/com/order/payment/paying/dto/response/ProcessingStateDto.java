package com.order.payment.paying.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.order.payment.paying.dto.enums.PaymentStatusDto;
import com.order.payment.paying.dto.enums.ProcessingStateStatusDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "status" })
public class ProcessingStateDto {

    @NotNull
    private ProcessingStateStatusDto status;
}
