package com.davikt.order.service.domain.mapper;

import com.davikt.domain.valueobject.CustomerId;
import com.davikt.domain.valueobject.Money;
import com.davikt.domain.valueobject.ProductId;
import com.davikt.domain.valueobject.RestaurantId;
import com.davikt.order.service.domain.dto.create.CreateOrderCommand;
import com.davikt.order.service.domain.dto.create.CreateOrderResponse;
import com.davikt.order.service.domain.dto.create.OrderAddress;
import com.davikt.order.service.domain.dto.track.TrackOrderResponse;
import com.davikt.order.service.domain.entity.Order;
import com.davikt.order.service.domain.entity.OrderItem;
import com.davikt.order.service.domain.entity.Product;
import com.davikt.order.service.domain.entity.Restaurant;
import com.davikt.order.service.domain.valueobject.StreetAddress;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId((createOrderCommand.restaurantId())))
                .products(createOrderCommand.items().stream()
                        .map(orderItem -> new Product(new ProductId(orderItem.productId()))).toList())
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.customerId()))
                .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.address()))
                .total(new Money(createOrderCommand.price()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.items()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return new CreateOrderResponse(
                order.getTrackingId().getValue(),
                order.getOrderStatus(),
                null
        );
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return new TrackOrderResponse(order.getTrackingId().getValue(), order.getOrderStatus(), order.getFailureMessages());
    }

    private List<OrderItem> orderItemsToOrderItemEntities(@NonNull List<com.davikt.order.service.domain.dto.create.OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItem.builder()
                        .product(new Product(new ProductId(orderItem.productId())))
                        .price(new Money(orderItem.price()))
                        .quantity(orderItem.quantity())
                        .subTotal(new Money(orderItem.subTotal()))
                        .build())
                .toList();
    }

    private StreetAddress orderAddressToStreetAddress(@NonNull OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(), address.street(), address.postalCode(), address.city());
    }


}
