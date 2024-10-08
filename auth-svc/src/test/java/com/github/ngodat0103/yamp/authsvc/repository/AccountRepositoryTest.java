package com.github.ngodat0103.yamp.authsvc.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
@Transactional
public class AccountRepositoryTest {

  @Autowired private AccountRepository accountRepository;

  @Test
  @DisplayName("Test Create account when not conflict")
  public void givenAccount_whenSave_returnSavedAccount() {
    Account account = new Account();
    account.setEmail("example@gmail.com");
    account.setUuid(UUID.randomUUID());
    account.setUsername("testUser");
    account.setPassword("bestPassword");
    Account saveAccount = accountRepository.save(account);

    Assertions.assertThat(saveAccount).isNotNull();
    Assertions.assertThat(saveAccount.getUuid()).isNotNull();
    System.out.println(saveAccount.getUuid());
    Assertions.assertThat(saveAccount.getPassword()).isEqualTo(account.getPassword());
    Assertions.assertThat(saveAccount.getUsername()).isEqualTo(account.getUsername());
    Assertions.assertThat(saveAccount.getEmail()).isEqualTo(account.getEmail());
    Assertions.assertThat(saveAccount.getPassword()).isNotNull();
  }
}
