package com.nttdata.productservices.infrastructure.service;

import com.nttdata.productservices.infrastructure.document.TypeCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeCreditService {

    Mono<TypeCredit> create(TypeCredit typeCredit);
    Flux<TypeCredit> getAll();
    Mono<TypeCredit> update(TypeCredit typeCredit);
    void delete(String id);
    Mono<TypeCredit> getTypeCredit(String id);
}
