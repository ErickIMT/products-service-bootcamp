package com.nttdata.productservices.infrastructure.repository;

import com.nttdata.productservices.infrastructure.document.BankAccount;
import com.nttdata.productservices.infrastructure.document.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

  Flux<Credit> findByCustomerId(String id);
}
