package com.davikt.order.service.domain.dto.message;

import com.davikt.domain.valueobject.PaymentStatus;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record PaymentResponse(
        @NonNull String id,
        @NonNull String sagaId,
        @NonNull String orderId,
        @NonNull String paymentId,
        @NonNull String customerId,
        @NonNull BigDecimal price,
        @NonNull Instant createdAt,
        @NonNull PaymentStatus paymentStatus
        ) {
}
