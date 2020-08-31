package com.order.payment.generic.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(of = { "description", "details" })
@JsonRootName(value = "error")
@JsonPropertyOrder({ "description", "details" })
public class ErrorDto {

    private String description;

    private String details;
}
