package com.davikt.order.service.domain.dto.track;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public record TrackOrderQuery(
        @NonNull UUID orderTrackingId
        ) {}
