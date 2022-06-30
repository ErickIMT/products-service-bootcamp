package com.nttdata.productservices.model.service;

import com.nttdata.productservices.model.document.Credit;
import com.nttdata.productservices.model.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<Credit> create(Credit credit);
    Flux<Credit> getAll();
    Mono<Credit> updateAmount(TransactionDTO transactionDTO);
    void delete(String id);
    Mono<Credit> getCredit(String id);
}
