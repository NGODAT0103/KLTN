package com.github.ngodat0103.yamp.authsvc.controller;

import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRequestDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.UpdateAccountDto;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping(value = "/accounts", produces = "application/json")
@RestController
@SecurityRequirement(name = "oauth2")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SCOPE_auth-service.write')")
// @Hidden
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponseDto register(@Valid @RequestBody AccountRequestDto accountRequestDto) {
    log.debug("Controller register method called");
    return accountService.register(accountRequestDto);
  }

  @GetMapping
  public Set<AccountResponseDto> getAccounts() {
    log.debug("Controller getAccounts method called");
    return accountService.getAccounts();
  }

  @GetMapping(path = "/{accountUuid}")
  @PreAuthorize("hasAuthority('SCOPE_auth-service.read')")
  public AccountResponseDto getAccount(@PathVariable UUID accountUuid) {
    log.debug("Controller getAccount method called");
    return accountService.getAccount(accountUuid);
  }

  @GetMapping(value = "/filter")
  public Set<AccountResponseDto> getAccountsFilterByRoles(
      @RequestParam(required = false) Set<String> roles,
      @RequestParam(required = false) UUID accountUuid,
      @RequestParam(required = false) String username) {
    return accountService.getAccountFilter(roles, accountUuid, username);
  }

  @PutMapping()
  public AccountResponseDto updateAccount(@RequestBody @Valid UpdateAccountDto updateAccountDto) {
    return accountService.updateAccount(updateAccountDto);
  }

  @DeleteMapping(path = "/logout")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void logout(HttpServletRequest request) {
    request.getSession().invalidate();
    SecurityContextHolder.clearContext();
  }
}
