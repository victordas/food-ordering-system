package com.davikt.order.service.domain.dto.track;

import com.davikt.domain.valueobject.OrderStatus;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Builder
public record TrackOrderResponse(
        @NonNull UUID orderTrackingId,
        @NonNull OrderStatus orderStatus,
        List<String> failureMessages
        ) {
}
