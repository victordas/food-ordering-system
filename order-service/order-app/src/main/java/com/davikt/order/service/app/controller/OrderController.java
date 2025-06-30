package com.davikt.order.service.app.controller;

import com.davikt.order.service.domain.dto.create.CreateOrderCommand;
import com.davikt.order.service.domain.dto.create.CreateOrderResponse;
import com.davikt.order.service.domain.dto.track.TrackOrderQuery;
import com.davikt.order.service.domain.dto.track.TrackOrderResponse;
import com.davikt.order.service.domain.ports.input.service.OrderAppService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderAppService orderAppService;

    public OrderController(OrderAppService orderAppService) {
        this.orderAppService = orderAppService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating order for customer: {} at restaurant: {}", createOrderCommand.customerId(),
                createOrderCommand.restaurantId());
        CreateOrderResponse createOrderResponse = orderAppService.createOrder(createOrderCommand);
        log.info("Order created with TrackingId: {}", createOrderResponse.orderTrackingId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrderResponse);
    }

    @GetMapping
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId) {
        TrackOrderResponse trackOrderResponse = orderAppService.trackOrder(new TrackOrderQuery(trackingId));
        log.info("Returning order status with tracking id: {}", trackingId);
        return ResponseEntity.ok(trackOrderResponse);
    }
}
