package com.davikt.order.service.domain;

import com.davikt.domain.valueobject.*;
import com.davikt.order.service.domain.dto.create.CreateOrderCommand;
import com.davikt.order.service.domain.dto.create.CreateOrderResponse;
import com.davikt.order.service.domain.dto.create.OrderAddress;
import com.davikt.order.service.domain.dto.create.OrderItem;
import com.davikt.order.service.domain.entity.Customer;
import com.davikt.order.service.domain.entity.Order;
import com.davikt.order.service.domain.entity.Product;
import com.davikt.order.service.domain.entity.Restaurant;
import com.davikt.order.service.domain.exception.OrderDomainException;
import com.davikt.order.service.domain.mapper.OrderDataMapper;
import com.davikt.order.service.domain.ports.input.service.OrderAppService;
import com.davikt.order.service.domain.ports.output.repository.CustomerRepository;
import com.davikt.order.service.domain.ports.output.repository.OrderRepository;
import com.davikt.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderAppServiceTest {

    @Autowired private OrderAppService orderAppService;
    @Autowired private OrderDataMapper orderDataMapper;
    @Autowired private OrderRepository orderRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongTotal;
    private CreateOrderCommand createOrderCommandWrongProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("a582debe-2f5d-41b2-9e25-254c4f2bc41d");
    private final UUID RESTAURANT_ID = UUID.fromString("1abc18f8-64df-4e97-8818-31cf39035822");
    private final UUID PRODUCT_ID = UUID.fromString("b51cfab3-4fe3-40ca-864f-cb0e62e76b4f");
    private final UUID ORDER_ID = UUID.fromString("9a284ede-3046-43be-9dd0-6b1f56f0fbc4");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void init() {
        createOrderCommand = new CreateOrderCommand(
                CUSTOMER_ID,
                RESTAURANT_ID,
                PRICE,
                List.of(
                        new OrderItem(PRODUCT_ID, 1, new BigDecimal("50.00"), new BigDecimal("50.00")),
                        new OrderItem(PRODUCT_ID, 3, new BigDecimal("50.00"), new BigDecimal("150.00"))
                ),
                new OrderAddress(
                        "street_1", "1000AB", "Paris"
                )
        );

        createOrderCommandWrongTotal = new CreateOrderCommand(
                CUSTOMER_ID,
                RESTAURANT_ID,
                new BigDecimal("250.00"),
                List.of(
                        new OrderItem(PRODUCT_ID, 1, new BigDecimal("50.00"), new BigDecimal("50.00")),
                        new OrderItem(PRODUCT_ID, 3, new BigDecimal("50.00"), new BigDecimal("150.00"))
                ),
                new OrderAddress(
                        "street_1", "1000AB", "Paris"
                )
        );

        createOrderCommandWrongProductPrice = new CreateOrderCommand(
                CUSTOMER_ID,
                RESTAURANT_ID,
                new BigDecimal("210.00"),
                List.of(
                        new OrderItem(PRODUCT_ID, 1, new BigDecimal("60.00"), new BigDecimal("60.00")),
                        new OrderItem(PRODUCT_ID, 3, new BigDecimal("50.00"), new BigDecimal("150.00"))
                ),
                new OrderAddress(
                        "street_1", "1000AB", "Paris"
                )
        );

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))
                ))
                .isActive(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrder() {
        CreateOrderResponse createOrderResponse = orderAppService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.orderStatus());
        assertEquals("Order created successfully", createOrderResponse.message());
        assertNotNull(createOrderResponse.orderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotal() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderAppService.createOrder(createOrderCommandWrongTotal));
        assertEquals("Total price: 250,00 is not equal to OrderItems total: 200,00.", orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderAppService.createOrder(createOrderCommandWrongProductPrice));
        assertEquals("Order item price  60,00 is no valid for " + PRODUCT_ID, orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithPassiveRestaurant() {
        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00")))
                ))
                .isActive(false)
                .build();
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).thenReturn(Optional.of(restaurantResponse));

        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderAppService.createOrder(createOrderCommand));
        assertEquals("Restaurant with id: " + RESTAURANT_ID +
                " is currently not active.", orderDomainException.getMessage());
    }
}
