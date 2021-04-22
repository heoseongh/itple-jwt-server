package com.heoseongh.jwttutorial.repository;

import com.heoseongh.jwttutorial.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // 계정을 가져올 때 권한 정보도 함께 조인해서 가져온다.
    // authorities 필드를 EAGER 모드로 fetch 한다.
    @EntityGraph(attributePaths = "authorities")
    Account findOneWithAuthoritiesByUsername(String username);
}
