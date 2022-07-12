package com.nttdata.productservices.infrastructure.service;

import com.nttdata.productservices.domain.event.CustomerCreatedEvent;
import com.nttdata.productservices.domain.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);

  @KafkaListener(
    topics = "${topic.customer.name:customers}",
    containerFactory = "kafkaListenerContainerFactory",
    groupId = "grupo1")
  public void consumer(Event<?> event) {
    if (event.getClass().isAssignableFrom(CustomerCreatedEvent.class)) {
      CustomerCreatedEvent customerCreatedEvent = (CustomerCreatedEvent) event;
      LOGGER.info("Received Customer created event .... with Id={}, data={}",
        customerCreatedEvent.getId(),
        customerCreatedEvent.getData().toString());
    }

  }
}
