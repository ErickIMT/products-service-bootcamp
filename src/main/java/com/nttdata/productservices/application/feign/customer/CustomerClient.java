package com.nttdata.productservices.application.feign.customer;

import com.nttdata.productservices.domain.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customer-service", url = "https://customer-service-bootcamp.azurewebsites.net/customers")
public interface CustomerClient {

    @GetMapping
    CustomerDTO getAllCustomers();

    @GetMapping("/{id}")
    CustomerDTO getCustomerById(@PathVariable("id") String id);

}
