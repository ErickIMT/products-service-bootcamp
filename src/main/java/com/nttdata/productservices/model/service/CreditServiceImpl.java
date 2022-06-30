package com.nttdata.productservices.model.service;

import com.nttdata.productservices.api.customer.CustomerClient;
import com.nttdata.productservices.model.document.Credit;
import com.nttdata.productservices.model.dto.TransactionDTO;
import com.nttdata.productservices.model.repository.CreditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private CustomerClient customerClient;

    @Override
    public Mono<Credit> create(Credit credit) {
        LOGGER.info("Iniciando Creacion de Credito");
        LOGGER.info("Buscando ID de cliente");
        return customerClient.getCustomerById(credit.getCustomerId())
                .switchIfEmpty(Mono.empty())
                .map( customerDTO -> {
                    LOGGER.info("Se encontro Id del cliente");
                    LOGGER.info("Generando Cuenta de Credito");
                    credit.setCreditAccount("2000-"+ Math.round(Math.random()*99999));
                    credit.setAmount(0);
                    if(credit.getTypeCredit().getId().equals("1")){
                        LOGGER.info("Asignando a Credito Personal");
                        credit.getTypeCredit().setName("Personal");
                    }else if(credit.getTypeCredit().getId().equals("2")){
                        LOGGER.info("Asignando a Credito Empresarial");
                        credit.getTypeCredit().setName("Empresarial");
                    }else if(credit.getTypeCredit().getId().equals("3")){
                        LOGGER.info("Asignando a Tarjeta de Credito");
                        credit.getTypeCredit().setName("TDC");
                    }
                    return credit;

                }).flatMap(c -> {
                    LOGGER.info("Guardando cuenta de credito en la BD");
                    return creditRepository.save(c);
                });
    }

    @Override
    public Flux<Credit> getAll() {

        LOGGER.info("Listando todas las cuentas de Credito");
        return creditRepository.findAll();
    }

    @Override
    public Mono<Credit> updateAmount(TransactionDTO transactionDTO) {
        LOGGER.info("Iniciando actualizacion de credito");
        LOGGER.info("Buscando credito por ID");

        return getCredit(transactionDTO.getAccountId())
                .switchIfEmpty(Mono.empty())
                .map(credit -> {
                    LOGGER.info("Se encontro credito con Id: "+transactionDTO.getAccountId());
                    float amount = credit.getAmount();

                    if(credit.getTypeCredit().getName().equals("TDC")){
                        LOGGER.info("Transaccion por Tarjeta de Credito");
                        if(amount + transactionDTO.getAmount() > credit.getLimit()){
                            LOGGER.warn("Consumo excede Limite de TDC");
                            credit.setId("0");
                        }else{
                            if(transactionDTO.getTypeTransaction().equals("Deposit")){
                                LOGGER.info("Abonando "+transactionDTO.getAmount() + "al credito");
                                credit.setAmount(amount - transactionDTO.getAmount());
                            }

                            if(transactionDTO.getTypeTransaction().equals("Withdraw")){
                                LOGGER.info("Usando "+transactionDTO.getAmount()+"del credito");
                                credit.setAmount(amount + transactionDTO.getAmount());
                            }
                        }
                    }else{
                        LOGGER.info("Transaccion en Credito bancario");
                        if(transactionDTO.getTypeTransaction().equals("Deposit")){
                            LOGGER.info("Abonando "+transactionDTO.getAmount() + "al credito");
                            credit.setAmount(amount - transactionDTO.getAmount());
                        }

                        if(transactionDTO.getTypeTransaction().equals("Withdraw")){
                            LOGGER.info("Usando "+transactionDTO.getAmount()+"del credito");
                            credit.setAmount(amount + transactionDTO.getAmount());
                        }
                    }

                    return credit;
                }).flatMap(credit -> {
                    if (credit.getId().equals("0")){
                        return Mono.empty();
                    }else{
                        LOGGER.info("Guardando credito actualizando en la BD");
                        return creditRepository.save(credit);
                    }
                });
    }

    @Override
    public void delete(String id) {
        creditRepository.deleteById(id);
    }

    @Override
    public Mono<Credit> getCredit(String id) {
        return creditRepository.findById(id);
    }
}
