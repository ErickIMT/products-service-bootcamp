package com.nttdata.productservices.application.api;

import com.nttdata.productservices.infrastructure.document.BankAccount;
import com.nttdata.productservices.domain.dto.TransactionDTO;
import com.nttdata.productservices.infrastructure.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controladoa de las cuentas bancarias.
 */
@RestController
@RequestMapping("/bankAccounts")
public class BankAccountController {

  @Autowired
  private BankAccountService bankAccountService;

  @GetMapping
  private Flux<BankAccount> getAllBankAccounts() {
    return bankAccountService.getAll();
  }

  @GetMapping("/{id}")
  private Mono<BankAccount> getBankAccountbyId(@PathVariable("id") String id) {
    return bankAccountService.getBankAccountById(id);
  }

  @PostMapping
  private Mono<BankAccount> saveBankAccount(@RequestBody BankAccount bankAccount) {
    return bankAccountService.create(bankAccount);
  }

  @PutMapping
  private Mono<BankAccount> updateBankAccountAmount(@RequestBody TransactionDTO transactionDto) {
    return bankAccountService.updateAmount(transactionDto);
  }

  @GetMapping("/user/{id}")
  private Flux<BankAccount> getbyCustomerId(@PathVariable("id") String id) {
    return bankAccountService.getBankAccountByCustomerId(id);
  }
}
