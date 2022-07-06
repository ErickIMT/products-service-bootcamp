package com.nttdata.productservices.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String id;
    private String accountId;
    private String typeTransaction;
    private String commerce;
    private float amount;
    @DateTimeFormat(pattern = "yyyy/MMMM/dd HH:mm:ss")
    private Date date;
}
