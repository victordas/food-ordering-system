package com.davikt.order.service.domain.dto.message;

import com.davikt.domain.valueobject.OrderApprovalStatus;
import lombok.Builder;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

@Builder
public record RestaurantApprovalResponse(
        @NonNull String id,
        @NonNull String sagaId,
        @NonNull String orderId,
        @NonNull String restaurantId,
        @NonNull Instant createdAt,
        @NonNull OrderApprovalStatus orderApprovalStatus,
        List<String>failureMessages
        ) {
}
