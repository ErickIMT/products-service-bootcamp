package com.nttdata.productservices.infrastructure.service;

import com.nttdata.productservices.infrastructure.document.BankAccount;
import com.nttdata.productservices.domain.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {

    Mono<BankAccount> create(BankAccount bankAccount);
    Flux<BankAccount> getAll();
    Mono<BankAccount> updateAmount(TransactionDTO transactionDTO);
    void delete(String id);
    Mono<BankAccount> getBankAccountById(String id);

    Flux<BankAccount> getBankAccountByCustomerId(String id);
}
