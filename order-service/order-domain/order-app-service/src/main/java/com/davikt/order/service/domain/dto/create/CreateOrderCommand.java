package com.davikt.order.service.domain.dto.create;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record CreateOrderCommand(
        @NonNull UUID customerId,
        @NonNull UUID restaurantId,
        @NonNull BigDecimal price,
        @NonNull List<OrderItem> items,
        @NonNull OrderAddress address
) {
}
