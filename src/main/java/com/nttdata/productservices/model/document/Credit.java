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
@AllArgsConstructor
@NoArgsConstructor
@Document("credits")
public class Credit {

    @Id
    private String id;
    private String creditAccount;
    private String customerId;
    private TypeCredit typeCredit;
    private float amount;
    private float limit;
}
