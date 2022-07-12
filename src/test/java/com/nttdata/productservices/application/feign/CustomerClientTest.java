package com.nttdata.productservices.application.feign;

import com.nttdata.productservices.application.feign.customer.CustomerClient;
import com.nttdata.productservices.domain.dto.CustomerDTO;
import com.nttdata.productservices.infrastructure.document.BankAccount;
import com.nttdata.productservices.infrastructure.service.CreditServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerClientTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private CreditServiceImpl creditService;

  @Test
  public void testGetAllCustomers() {

    webTestClient.get().uri("localhost:8091/customers")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(CustomerDTO.class);

  }

  @Test
  public void testGetBankAccountByCustomerId() {
    webTestClient.get().uri("/bankAccounts/user/62c454256633ef752efcea65")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(BankAccount.class);
  }
}
