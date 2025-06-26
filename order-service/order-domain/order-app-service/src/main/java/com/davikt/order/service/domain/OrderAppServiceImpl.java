package com.davikt.order.service.domain;

import com.davikt.order.service.domain.dto.create.CreateOrderCommand;
import com.davikt.order.service.domain.dto.create.CreateOrderResponse;
import com.davikt.order.service.domain.dto.track.TrackOrderQuery;
import com.davikt.order.service.domain.dto.track.TrackOrderResponse;
import com.davikt.order.service.domain.ports.input.service.OrderAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderAppServiceImpl implements OrderAppService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    OrderAppServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }


    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
