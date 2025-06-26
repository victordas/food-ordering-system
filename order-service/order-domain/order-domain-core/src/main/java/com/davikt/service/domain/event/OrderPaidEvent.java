package com.davikt.service.domain.event;

import com.davikt.domain.event.DomainEvent;
import com.davikt.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {

    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}