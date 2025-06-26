package com.davikt.order.service.domain.valueobject;

import com.davikt.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
