package com.davikt.domain.event.publisher;

import com.davikt.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent<?>> {

     void publish(T domainEvent);
}
