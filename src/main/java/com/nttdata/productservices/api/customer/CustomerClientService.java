package com.nttdata.productservices.api.customer;

import com.nttdata.productservices.model.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CustomerClientService {

    @Autowired
    private CustomerClient customerClient;

    private Flux<CustomerDTO> getAllCustomers(){
        return customerClient.getAllCustomers();
    }

    private Mono<CustomerDTO> getCustomerById(String id){
        return customerClient.getCustomerById(id);
    }
}
