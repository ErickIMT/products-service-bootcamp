package com.nttdata.productservices.model.service;

import com.nttdata.productservices.model.document.BankAccount;
import com.nttdata.productservices.model.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {

    Mono<BankAccount> create(BankAccount bankAccount);
    Flux<BankAccount> getAll();
    Mono<BankAccount> updateAmount(TransactionDTO transactionDTO);
    void delete(String id);
    Mono<BankAccount> getBankAccountById(String id);

    Mono<BankAccount> getBankAccountByCustomerId(String id);
}
