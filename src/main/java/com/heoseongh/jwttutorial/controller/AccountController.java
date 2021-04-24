package com.heoseongh.jwttutorial.controller;

import com.heoseongh.jwttutorial.dto.AccountDto;
import com.heoseongh.jwttutorial.entity.Account;
import com.heoseongh.jwttutorial.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<Account> signup(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.signUp(accountDto));
    }

    @GetMapping("/user")
    public ResponseEntity<Account> getMyAccountInfo() {
        return ResponseEntity.ok(accountService.getMyAccountWithAuthorities());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Account> getAccountInfo(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountWithAuthorities(username));
    }
}
