package com.nttdata.productservices.model.service;

import com.nttdata.productservices.model.document.TypeBankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeBankAccountService {
    
    Mono<TypeBankAccount> create(TypeBankAccount typeBankAccount);
    Flux<TypeBankAccount> getAll();
    Mono<TypeBankAccount> update(TypeBankAccount typeBankAccount);
    void delete(String id);
    Mono<TypeBankAccount> getTypeBankAccount(String id);
    
}
