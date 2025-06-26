package com.davikt.order.service.domain;

import com.davikt.order.service.domain.entity.Order;
import com.davikt.order.service.domain.entity.Restaurant;
import com.davikt.order.service.domain.event.OrderCancelledEvent;
import com.davikt.order.service.domain.event.OrderCreatedEvent;
import com.davikt.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPaid(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
