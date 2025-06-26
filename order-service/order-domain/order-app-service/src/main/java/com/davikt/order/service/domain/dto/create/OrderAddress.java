package com.davikt.order.service.domain.dto.create;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record OrderAddress(
        @NonNull @Max(value = 50) String street,
        @NonNull @Max(value = 6) String postalCode,
        @NonNull @Max(value = 50) String city
) {
}