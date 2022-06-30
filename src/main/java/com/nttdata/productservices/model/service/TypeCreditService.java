package com.nttdata.productservices.model.service;

import com.nttdata.productservices.model.document.TypeCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeCreditService {

    Mono<TypeCredit> create(TypeCredit typeCredit);
    Flux<TypeCredit> getAll();
    Mono<TypeCredit> update(TypeCredit typeCredit);
    void delete(String id);
    Mono<TypeCredit> getTypeCredit(String id);
}
