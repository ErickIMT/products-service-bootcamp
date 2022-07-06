package com.nttdata.productservices.infrastructure.repository;

import com.nttdata.productservices.infrastructure.document.TypeCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCreditRepository extends ReactiveMongoRepository<TypeCredit, String> {
}
