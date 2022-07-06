package com.nttdata.productservices.infrastructure.service;

import com.nttdata.productservices.application.feign.customer.CustomerClient;
import com.nttdata.productservices.infrastructure.repository.CreditRepository;
import com.nttdata.productservices.infrastructure.document.Credit;
import com.nttdata.productservices.domain.dto.CustomerDTO;
import com.nttdata.productservices.domain.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio para operaciones con Creditos.
 */
@Service
public class CreditServiceImpl implements CreditService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);
  @Autowired
  private CreditRepository creditRepository;
  @Autowired
  private CustomerClient customerClient;

  @Override
  public Mono<Credit> create(Credit credit) {
    LOGGER.info("Iniciando Creacion de Credito");
    LOGGER.info("Buscando ID de cliente");

    CustomerDTO customerDto = customerClient.getCustomerById(credit.getCustomerId());

    if (customerDto == null) {
      LOGGER.warn("No se encontro usuario con id " + credit.getCustomerId());
      return Mono.empty();
    }

    LOGGER.info("Se encontro Id del cliente");
    LOGGER.info("Generando Cuenta de Credito");
    credit.setCreditAccount("2000-" + Math.round(Math.random() * 99999));
    credit.setAmount(0);

    if (credit.getTypeCredit().getId().equals("1")) {
      LOGGER.info("Asignando a Credito Personal");
      credit.getTypeCredit().setName("Personal");
    } else if (credit.getTypeCredit().getId().equals("2")) {
      LOGGER.info("Asignando a Credito Empresarial");
      credit.getTypeCredit().setName("Empresarial");
    } else if (credit.getTypeCredit().getId().equals("3")) {
      LOGGER.info("Asignando a Tarjeta de Credito");
      credit.getTypeCredit().setName("TDC");
    }


    LOGGER.info("Guardando cuenta de credito en la BD");
    return creditRepository.save(credit);

  }

  @Override
  public Flux<Credit> getAll() {

    LOGGER.info("Listando todas las cuentas de Credito");
    return creditRepository.findAll();
  }

  @Override
  public Mono<Credit> updateAmount(TransactionDTO transactionDto) {
    LOGGER.info("Iniciando actualizacion de credito");
    LOGGER.info("Buscando credito por ID");

    return getCredit(transactionDto.getAccountId())
      .switchIfEmpty(Mono.empty())
      .map(credit -> {
        LOGGER.info("Se encontro credito con Id: " + transactionDto.getAccountId());
        float amount = credit.getAmount();

        if (credit.getTypeCredit().getName().equals("TDC")) {
          LOGGER.info("Transaccion por Tarjeta de Credito");
          if (amount + transactionDto.getAmount() > credit.getLimit()) {
            LOGGER.warn("Consumo excede Limite de TDC");
            credit.setId("0");
          } else {
            if (transactionDto.getTypeTransaction().equals("Deposit")) {
              LOGGER.info("Abonando " + transactionDto.getAmount() + "al credito");
              credit.setAmount(amount - transactionDto.getAmount());
            }

            if (transactionDto.getTypeTransaction().equals("Withdraw")) {
              LOGGER.info("Usando " + transactionDto.getAmount() + "del credito");
              credit.setAmount(amount + transactionDto.getAmount());
            }
          }
        } else {
          LOGGER.info("Transaccion en Credito bancario");
          if (transactionDto.getTypeTransaction().equals("Deposit")) {
            LOGGER.info("Abonando " + transactionDto.getAmount() + "al credito");
            credit.setAmount(amount - transactionDto.getAmount());
          }

          if (transactionDto.getTypeTransaction().equals("Withdraw")) {
            LOGGER.info("Usando " + transactionDto.getAmount() + "del credito");
            credit.setAmount(amount + transactionDto.getAmount());
          }
        }

        return credit;
      }).flatMap(credit -> {
        if (credit.getId().equals("0")) {
          return Mono.empty();
        } else {
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

  @Override
  public Flux<Credit> getCreditByCustomerId(String id) {
    return creditRepository.findByCustomerId(id);
  }
}
