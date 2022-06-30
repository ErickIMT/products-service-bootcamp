package com.nttdata.productservices.api.customer;

import com.nttdata.productservices.model.dto.CustomerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(value = "customer-service", url = "localhost:8091/customers")
public interface CustomerClient {

    @GetMapping
    Flux<CustomerDTO> getAllCustomers();

    @GetMapping("/{id}")
    Mono<CustomerDTO> getCustomerById(@PathVariable String id);

}
