package com.nttdata.productservices.domain.event;

import com.nttdata.productservices.domain.dto.CustomerDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCreatedEvent extends Event<CustomerDTO> {

}
