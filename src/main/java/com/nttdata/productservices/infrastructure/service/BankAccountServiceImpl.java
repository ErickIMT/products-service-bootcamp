package com.nttdata.productservices.infrastructure.service;

import com.nttdata.productservices.application.feign.customer.CustomerClient;
import com.nttdata.productservices.domain.dto.CustomerDTO;
import com.nttdata.productservices.domain.dto.TransactionDTO;
import com.nttdata.productservices.infrastructure.document.BankAccount;
import com.nttdata.productservices.infrastructure.repository.BankAccountRepository;
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

    CustomerDTO customerDto = customerClient.getCustomerById(bankAccount.getCustomerId());

    /*Validar si existe el id del Cliente*/
    if (customerDto == null) {
      LOGGER.warn("No se encontro usuario con id " + bankAccount.getCustomerId());
      return Mono.error(new Exception("Id Usuario no se encuentra registrado"));
    }

    /*Validacion del tipo de cliente*/
    /*Tipo de Cliente Personal*/
    if (customerDto.getCustomerType().getName().equals("Personal")) {
      LOGGER.info("Customer Type: Personal");
      LOGGER.info("Buscando si cliente ya tiene cuenta bancaria");
      LOGGER.info("Cliente no tiene cuenta bancaria");

      /*Se identifica tipo de cuenta que se va a crear*/
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


      /*Tipo de Cliente Empresarial*/
    } else if (customerDto.getCustomerType().getName().equals("Empresarial")) {
      LOGGER.info("Customer Type: Empresarial");
      /*Identificando tipo de cuenta a crear*/
      if (bankAccount.getTypeBankAccount().getId().equals("2")) {
        LOGGER.info("Seteando Typo Cuenta: Corriente");
        bankAccount.getTypeBankAccount().setName("Corriente");

        LOGGER.info("Generando numero de cuenta");
        bankAccount.setAccount("1000-" + Math.round(Math.random() * 99999));
      } else {
        LOGGER.error("Cliente Empresarial, solo puede crear cuentas corrientes");
        return Mono.error(new Exception("Cliente Empresarial solo puede crear cuentas corrientes"));
      }
    }
    return bankAccountRepository.save(bankAccount);
  }

  @Override
  public Flux<BankAccount> getAll() {

    LOGGER.info("Listando todas las cuentas bancarias");
    return bankAccountRepository.findAll();
  }

  @Override
  public Mono<BankAccount> updateAmount(TransactionDTO transactionDto) {
    LOGGER.info("Iniciando Actualizacion del Monto en Cuenta Bancaria");
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
  public Flux<BankAccount> getBankAccountByCustomerId(String id) {
    return bankAccountRepository.findByCustomerId(id);
  }

}
