package com.nttdata.productservices.model.document;

import com.nttdata.productservices.model.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("bank_accounts")
public class BankAccount {

    @Id
    private String id;
    private String customerId;
    private String account;
    private TypeBankAccount typeBankAccount;
    private float amount;

}
