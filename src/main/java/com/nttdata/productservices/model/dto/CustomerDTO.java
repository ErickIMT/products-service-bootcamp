package com.nttdata.productservices.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String id;
    private String name;
    private String lastName;
    private String document;
    private String address;
    private String email;
    private String phone;
    private CustomerTypeDTO customerType;
}
