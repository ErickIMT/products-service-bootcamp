package com.nttdata.productservices.model.repository;

import com.nttdata.productservices.model.document.TypeBankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeBankRepository extends ReactiveMongoRepository<TypeBankAccount, String> {
}
