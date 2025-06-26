package com.davikt.order.service.domain.ports.output.message.publisher.payment;

import com.davikt.domain.event.publisher.DomainEventPublisher;
import com.davikt.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
