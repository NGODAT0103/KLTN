package com.example.userservice.persistence.repository.billing;

import com.example.userservice.persistence.entity.billing.BillingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface BillingDetailRepository <T extends BillingDetail > extends JpaRepository<T, UUID> {
    Set<T> findByCustomerUuid(UUID customerUuid);
}
