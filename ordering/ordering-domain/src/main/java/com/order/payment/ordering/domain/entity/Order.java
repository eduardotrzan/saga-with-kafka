package com.order.payment.ordering.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.order.payment.generic.domain.entity.AbstractEntity;
import com.order.payment.ordering.domain.entity.enums.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordering")
@ToString(callSuper = true, of = { "id" })
public class Order extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordering_id_seq")
    @SequenceGenerator(name = "ordering_id_seq", sequenceName = "ordering_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "status", nullable = false, length = 70)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "cost", nullable = false, precision = 24, scale = 12)
    private BigDecimal cost;

    @Builder
    public Order(
            UUID uuid,
            OffsetDateTime createDate,
            OffsetDateTime updateDate,
            Long version,
            Long id,
            OrderStatus status,
            String description,
            BigDecimal cost
    ) {
        super(uuid, createDate, updateDate, version);
        this.id = id;
        this.status = status;
        this.description = description;
        this.cost = cost;
    }

}
