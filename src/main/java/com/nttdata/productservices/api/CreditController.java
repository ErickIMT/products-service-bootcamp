package com.nttdata.productservices.api;

import com.nttdata.productservices.model.document.BankAccount;
import com.nttdata.productservices.model.document.Credit;
import com.nttdata.productservices.model.dto.TransactionDTO;
import com.nttdata.productservices.model.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credits")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @GetMapping
    private Flux<Credit> getAllCredits(){
        return creditService.getAll();
    }

    @GetMapping("/{id}")
    private Mono<Credit> getCreditById(@PathVariable("id") String id){
        return creditService.getCredit(id);    }

    @PostMapping
    private Mono<Credit> createCredit(@RequestBody Credit credit){
        return creditService.create(credit);
    }

    @PutMapping
    private Mono<Credit> updateCreditAmount(@RequestBody TransactionDTO transactionDTO){
        return creditService.updateAmount(transactionDTO);
    }

}
