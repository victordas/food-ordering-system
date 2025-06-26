package com.davikt.order.service.domain.ports.output.repository;

import com.davikt.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);


}
