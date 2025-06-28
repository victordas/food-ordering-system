package com.davikt.order.service.domain;

import com.davikt.order.service.domain.dto.create.CreateOrderCommand;
import com.davikt.order.service.domain.dto.create.CreateOrderResponse;
import com.davikt.order.service.domain.event.OrderCreatedEvent;
import com.davikt.order.service.domain.mapper.OrderDataMapper;
import com.davikt.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper, OrderDataMapper orderDataMapper, OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
       OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
       orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return  orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(), "Order created successfully");
    }

}
