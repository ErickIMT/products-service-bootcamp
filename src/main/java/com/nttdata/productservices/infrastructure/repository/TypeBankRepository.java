package com.nttdata.productservices.infrastructure.repository;

import com.nttdata.productservices.infrastructure.document.TypeBankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeBankRepository extends ReactiveMongoRepository<TypeBankAccount, String> {
}
