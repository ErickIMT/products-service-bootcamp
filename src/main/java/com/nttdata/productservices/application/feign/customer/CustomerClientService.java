package com.nttdata.productservices.application.feign.customer;

import com.nttdata.productservices.domain.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerClientService {

    @Autowired
    private CustomerClient customerClient;

    private CustomerDTO getAllCustomers(){
        return customerClient.getAllCustomers();
    }

    private CustomerDTO getCustomerById(String id){
        return customerClient.getCustomerById(id);
    }
}
