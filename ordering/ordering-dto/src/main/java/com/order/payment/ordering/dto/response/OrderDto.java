package com.order.payment.ordering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.order.payment.ordering.dto.enums.OrderStatusDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "uuid", "createDate", "updateDate", "status", "description", "cost" })
public class OrderDto {

    private UUID uuid;

    private OffsetDateTime createDate;

    private OffsetDateTime updateDate;

    private OrderStatusDto status;

    private String description;

    private BigDecimal cost;
}
