package com.order.payment.paying.domain.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.payment.paying.domain.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByUuid(UUID uuid);
}
