package com.order.payment.orch.service.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.order.payment.orch.dto.response.PaymentDto;
import com.order.payment.orch.service.mapper.PaymentMapper;
import com.order.payment.paying.api.PaymentsManagementApi;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentsManagementApi paymentsManagementApi;

    private final PaymentMapper paymentMapper;

    public List<PaymentDto> findByOrderUuid(UUID orderUuid) {
        List<com.order.payment.paying.dto.PaymentDto> payments = paymentsManagementApi.findByOrderUuid(orderUuid);
        return paymentMapper.toDtos(payments);
    }
}
