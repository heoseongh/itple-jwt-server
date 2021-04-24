package com.heoseongh.jwttutorial.service;

import com.heoseongh.jwttutorial.dto.AccountDto;
import com.heoseongh.jwttutorial.entity.Account;
import com.heoseongh.jwttutorial.entity.Authority;
import com.heoseongh.jwttutorial.repository.AccountRepository;
import com.heoseongh.jwttutorial.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = false)
    public Account signUp(AccountDto accountDto) {

        Account findAccount = accountRepository.findOneWithAuthoritiesByUsername(accountDto.getUsername());
        if(findAccount != null) {
            throw new RuntimeException("이미 가입되어있는 계정입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Account account = Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .nickname(accountDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return accountRepository.save(account);
    }

    // username으로 Account를 가져온다.
    public Account getAccountWithAuthorities(String username) {
        return accountRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 SecurityContext에 저장된 username으로 Account를 가져온다.
    public Account getMyAccountWithAuthorities() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        return accountRepository.findOneWithAuthoritiesByUsername(currentUsername);
    }

}
