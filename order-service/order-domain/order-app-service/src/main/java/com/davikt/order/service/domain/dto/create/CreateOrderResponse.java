package com.davikt.order.service.domain.dto.create;

import com.davikt.domain.valueobject.OrderStatus;
import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public record CreateOrderResponse(
        @NonNull UUID orderTrackingId,
        @NonNull OrderStatus orderStatus,
        @NonNull String message
) {
}
