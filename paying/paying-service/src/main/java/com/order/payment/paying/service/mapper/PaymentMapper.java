package com.order.payment.paying.service.mapper;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.paying.domain.entity.Payment;
import com.order.payment.paying.dto.enums.PaymentStatusDto;
import com.order.payment.paying.dto.request.PaymentCreateDto;
import com.order.payment.paying.dto.response.PaymentDto;

@RequiredArgsConstructor
@Component
public class PaymentMapper {

    public Payment toNewEntity(PaymentCreateDto request) {
        Objects.requireNonNull(request);

        return Payment.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .orderUuid(request.getOrderUuid())
                .build();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<PaymentDto> toDto(Payment entity) {
        if (entity == null) {
            return Optional.empty();
        }

        PaymentDto dto = buildDto(entity);
        return Optional.of(dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<PaymentDto> toDtos(List<Payment> entities) {
        return Objects.requireNonNullElse(entities, Collections.<Payment>emptyList())
                .stream()
                .map(this::buildDto)
                .collect(Collectors.toList());
    }

    private PaymentDto buildDto(Payment entity) {
        PaymentStatusDto status = PaymentStatusDto.valueOf(entity.getStatus().name());

        return PaymentDto.builder()
                .uuid(entity.getUuid())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .status(status)
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .build();
    }
}
