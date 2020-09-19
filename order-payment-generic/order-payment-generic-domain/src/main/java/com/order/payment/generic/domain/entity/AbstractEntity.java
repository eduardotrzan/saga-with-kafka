package com.order.payment.generic.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@Access(AccessType.FIELD)
@ToString(of = { "uuid", "createDate", "updateDate", "version" })
public abstract class AbstractEntity<ID> implements Serializable {

    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, updatable = false, name = "create_date")
    private OffsetDateTime createDate;

    @Column(nullable = false, name = "update_date")
    private OffsetDateTime updateDate;

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID();
        this.createDate = OffsetDateTime.now();
        this.updateDate = this.createDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = OffsetDateTime.now();
    }

    public abstract ID getId();

    public abstract void setId(ID id);
}
