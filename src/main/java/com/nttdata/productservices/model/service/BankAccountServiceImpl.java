package com.nttdata.productservices.model.service;

import com.nttdata.productservices.api.customer.CustomerClient;
import com.nttdata.productservices.model.document.BankAccount;
import com.nttdata.productservices.model.dto.CustomerDTO;
import com.nttdata.productservices.model.dto.TransactionDTO;
import com.nttdata.productservices.model.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * Clase Servicio que implementa BanckaccountService.
 */

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);
  @Autowired
  private BankAccountRepository bankAccountRepository;

  @Autowired
  private CustomerClient customerClient;

  @Override
  public Mono<BankAccount> create(BankAccount bankAccount) {

    LOGGER.info("Iniciando Creacion de Cuenta Bancaria");
    return customerClient.getCustomerById(bankAccount.getCustomerId())
        .switchIfEmpty(Mono.empty())
        .map(customerDTO -> {
          LOGGER.info("Se encontro usuario: "
              + customerDTO.getName() + " " + customerDTO.getLastName());

          if (customerDTO.getCustomerType().getName().equals("Personal")) {
            LOGGER.info("Customer Type: Personal");
            LOGGER.info("Buscando si cliente ya tiene cuenta bancaria");

            if (getBankAccountByCustomerId(customerDTO.getId()) != null) {
              LOGGER.error("CLIENTE PERSONAL YA TIENE UNA CUENTA BANCARIA");
            } else {
              LOGGER.info("Cliente no tiene cuenta bancaria");
              if (bankAccount.getTypeBankAccount().getId().equals("1")) {
                LOGGER.info("Seteando Typo Cuenta: Ahorro");
                bankAccount.getTypeBankAccount().setName("Ahorro");
              } else if (bankAccount.getTypeBankAccount().getId().equals("2")) {
                LOGGER.info("Seteando Typo Cuenta: Corriente");
                bankAccount.getTypeBankAccount().setName("Corriente");
              } else if (bankAccount.getTypeBankAccount().getId().equals("3")) {
                LOGGER.info("Seteando Typo Cuenta: Plazo Fijo");
                bankAccount.getTypeBankAccount().setName("Plazo Fijo");
              }
              LOGGER.info("Generando numero de cuenta");
              bankAccount.setAccount("1000-" + Math.round(Math.random() * 99999));
            }
          } else if (customerDTO.getCustomerType().getName().equals("Empresarial")) {
            LOGGER.info("Customer Type: Empresarial");
            if (bankAccount.getTypeBankAccount().getId().equals("2")) {
              LOGGER.info("Seteando Typo Cuenta: Corriente");
              bankAccount.getTypeBankAccount().setName("Corriente");

              LOGGER.info("Generando numero de cuenta");
              bankAccount.setAccount("1000-" + Math.round(Math.random() * 99999));
            } else {
              LOGGER.error("Cliente Empresarial, solo puede crear cuentas corrientes");
            }
          }

          return bankAccount;

        }).flatMap(ba -> {

          if (ba.getAccount().equals(null)) {
            return Mono.empty();
          } else {
            LOGGER.warn("Guardando Cuenta en BD");
            return bankAccountRepository.save(ba);
          }
        });
  }

  @Override
  public Flux<BankAccount> getAll() {

    LOGGER.info("Listando todas las cuentas bancarias");
    return bankAccountRepository.findAll();
  }

  @Override
  public Mono<BankAccount> updateAmount(TransactionDTO transactionDto) {
    LOGGER.info("Iniciando Actualizacin del Monto en Cuenta Bancaria");
    LOGGER.info("Buscando AccountId en la BD");
    return getBankAccountById(transactionDto.getAccountId())
        .switchIfEmpty(Mono.empty())
        .map(bankAccount -> {
          LOGGER.info("Se encontro Cuenta con Id: " + transactionDto.getAccountId());
          float amount = bankAccount.getAmount();

          if (transactionDto.getTypeTransaction().equals("Deposit")) {
            LOGGER.info("Depositando " + transactionDto.getAmount() + " a la cuenta");
            bankAccount.setAmount(amount + transactionDto.getAmount());
          }

          if (transactionDto.getTypeTransaction().equals("Withdraw")) {
            LOGGER.info("Retirando " + transactionDto.getAmount() + " de la cuenta");
            bankAccount.setAmount(amount - transactionDto.getAmount());
          }

          return bankAccount;
        }).flatMap(bankAccount -> {
          LOGGER.info("Guardando cambios en la BD");
          return bankAccountRepository.save(bankAccount);
        });
  }

  @Override
  public void delete(String id) {
    bankAccountRepository.deleteById(id);
  }

  @Override
  public Mono<BankAccount> getBankAccountById(String id) {
    return bankAccountRepository.findById(id);
  }

  @Override
  public Mono<BankAccount> getBankAccountByCustomerId(String id) {
    return bankAccountRepository.findByCustomerId(id);
  }


}
