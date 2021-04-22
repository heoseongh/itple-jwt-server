package com.heoseongh.jwttutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Size;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    // 패스워드 필드가 Response 되지 않도록 하자.
    // Access 타입을 WRITE_ONLY로 설정하게 되면
    // WRITE 시에만(deserialize 할 때만) 해당 필드에 접근이 가능하게 된다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;
}
