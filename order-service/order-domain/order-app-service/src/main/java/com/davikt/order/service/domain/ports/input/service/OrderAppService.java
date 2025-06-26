package com.davikt.order.service.domain.ports.input.service;

import com.davikt.order.service.domain.dto.create.CreateOrderCommand;
import com.davikt.order.service.domain.dto.create.CreateOrderResponse;
import com.davikt.order.service.domain.dto.track.TrackOrderQuery;
import com.davikt.order.service.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderAppService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
