package com.order.payment.orch.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "description", "cost" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreateDto {

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private BigDecimal cost;
}
