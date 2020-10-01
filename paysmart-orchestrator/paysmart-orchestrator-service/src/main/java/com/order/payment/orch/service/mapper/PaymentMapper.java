package com.order.payment.orch.service.mapper;

import lombok.RequiredArgsConstructor;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.order.payment.orch.dto.enums.PaymentStatusDto;
import com.order.payment.orch.dto.response.PaymentDto;

@RequiredArgsConstructor
@Component
public class PaymentMapper {

    public List<PaymentDto> toDtos(List<com.order.payment.paying.dto.PaymentDto> serviceDtos) {
        return Objects.requireNonNullElse(serviceDtos, Collections.<com.order.payment.paying.dto.PaymentDto>emptyList())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PaymentDto toDto(com.order.payment.paying.dto.PaymentDto serviceDto) {
        Objects.requireNonNull(serviceDto);

        PaymentStatusDto status = PaymentStatusDto.valueOf(serviceDto.getStatus().name());

        return PaymentDto.builder()
                .uuid(serviceDto.getUuid())
                .createDate(serviceDto.getCreateDate().atOffset(ZoneOffset.UTC))
                .updateDate(serviceDto.getUpdateDate().atOffset(ZoneOffset.UTC))
                .status(status)
                .description(serviceDto.getDescription())
                .amount(serviceDto.getAmount())
                .build();
    }
}
