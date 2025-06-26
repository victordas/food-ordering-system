package com.davikt.order.service.domain.dto.create;

import com.davikt.domain.valueobject.OrderStatus;
import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.util.UUID;

public record CreateOrderResponse(
        @NonNull UUID orderTrackingId,
        @NonNull OrderStatus orderStatus,
        @Nullable String message
) {
}
