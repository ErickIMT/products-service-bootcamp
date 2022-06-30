package com.nttdata.productservices.model.repository;

import com.nttdata.productservices.model.document.TypeCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCreditRepository extends ReactiveMongoRepository<TypeCredit, String> {
}
