package com.nttdata.productservices.infrastructure.repository;

import com.nttdata.productservices.infrastructure.document.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {

    Flux<BankAccount> findByCustomerId(String id);
}
