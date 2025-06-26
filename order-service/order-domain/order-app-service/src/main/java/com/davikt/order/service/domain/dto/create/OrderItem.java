package com.davikt.order.service.domain.dto.create;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderItem(
        @NonNull UUID productId,
        @NonNull Integer quantity,
        @NonNull BigDecimal price,
        @NonNull BigDecimal subTotal
) {}
