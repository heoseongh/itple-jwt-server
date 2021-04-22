package com.heoseongh.jwttutorial.service;

import com.heoseongh.jwttutorial.entity.Account;
import com.heoseongh.jwttutorial.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findOneWithAuthoritiesByUsername(username);
        if(account == null) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다.");
        }

        return createAccount(account);
    }

    private User createAccount(Account account) {
        if(!account.isActivated()) {
            throw new RuntimeException("계정이 비활성화 상태입니다.");
        }
        List<SimpleGrantedAuthority> authorities = account.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new User(account.getUsername(), account.getPassword(), authorities);
    }


}
