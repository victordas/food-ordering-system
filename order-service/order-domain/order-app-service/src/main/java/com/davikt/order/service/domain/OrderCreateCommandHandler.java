package com.davikt.order.service.domain;

import com.davikt.order.service.domain.dto.create.CreateOrderCommand;
import com.davikt.order.service.domain.dto.create.CreateOrderResponse;
import com.davikt.order.service.domain.entity.Customer;
import com.davikt.order.service.domain.entity.Order;
import com.davikt.order.service.domain.entity.Restaurant;
import com.davikt.order.service.domain.event.OrderCreatedEvent;
import com.davikt.order.service.domain.exception.OrderDomainException;
import com.davikt.order.service.domain.mapper.OrderDataMapper;
import com.davikt.order.service.domain.ports.output.repository.CustomerRepository;
import com.davikt.order.service.domain.ports.output.repository.OrderRepository;
import com.davikt.order.service.domain.ports.output.repository.RestaurantRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderCreateCommandHandler.class);
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService,
                                     OrderRepository orderRepository,
                                     CustomerRepository customerRepository,
                                     RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {

        checkCustomer(createOrderCommand.customerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order orderResult = saveOrder(order);
        log.info("OrderId: {} is created", orderResult.getId().getValue());
        return  orderDataMapper.orderToCreateOrderResponse(orderResult);
    }

    private void checkCustomer(@NonNull UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with id: {}", customerId);
            throw  new OrderDomainException("Could not find customer with id: " + customer);
        }
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find restaurant with id: {}", createOrderCommand.restaurantId());
            throw  new OrderDomainException("Could not find customer with id: " + createOrderCommand.restaurantId());
        }
        return optionalRestaurant.get();
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            throw new OrderDomainException("Failed to save the order");
        }
        log.info("OrderId: {} is saved successfully", order.getId().getValue());
        return orderResult;
    }

}
