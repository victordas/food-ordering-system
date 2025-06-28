package com.davikt.order.service.domain;

import com.davikt.order.service.domain.dto.track.TrackOrderQuery;
import com.davikt.order.service.domain.dto.track.TrackOrderResponse;
import com.davikt.order.service.domain.entity.Order;
import com.davikt.order.service.domain.exception.OrderNotFoundException;
import com.davikt.order.service.domain.mapper.OrderDataMapper;
import com.davikt.order.service.domain.ports.output.repository.OrderRepository;
import com.davikt.order.service.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderTrackCommandHandler.class);
    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }


    @Transactional(readOnly = true)
    TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> orderResult = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.orderTrackingId()));

        if (orderResult.isEmpty()) {
            log.warn("Could not find the order with tracking id: {}", trackOrderQuery.orderTrackingId());
            throw new OrderNotFoundException("Could not find the order with tracking id: " + trackOrderQuery.orderTrackingId());
        }

        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
