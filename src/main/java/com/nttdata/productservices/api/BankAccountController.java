package com.nttdata.productservices.api;

import com.nttdata.productservices.api.customer.CustomerClient;
import com.nttdata.productservices.api.customer.CustomerClientService;
import com.nttdata.productservices.model.document.BankAccount;
import com.nttdata.productservices.model.dto.CustomerDTO;
import com.nttdata.productservices.model.dto.TransactionDTO;
import com.nttdata.productservices.model.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controladoa de las cuentas y creditos bancarios.
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

}
