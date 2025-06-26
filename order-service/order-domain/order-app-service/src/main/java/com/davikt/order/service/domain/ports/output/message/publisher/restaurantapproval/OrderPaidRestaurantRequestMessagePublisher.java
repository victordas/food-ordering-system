package com.davikt.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.davikt.domain.event.publisher.DomainEventPublisher;
import com.davikt.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
