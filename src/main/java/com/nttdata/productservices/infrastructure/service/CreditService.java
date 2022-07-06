package com.nttdata.productservices.infrastructure.service;

import com.nttdata.productservices.infrastructure.document.Credit;
import com.nttdata.productservices.domain.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<Credit> create(Credit credit);
    Flux<Credit> getAll();
    Mono<Credit> updateAmount(TransactionDTO transactionDTO);
    void delete(String id);
    Mono<Credit> getCredit(String id);

    Flux<Credit> getCreditByCustomerId(String id);
}
