package com.warders.api.user.service.vo;

import com.warders.api.auth.controller.dto.SignUpRequest;
import com.warders.api.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserVo {

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private String userName;

    private Role role;

    public static CreateUserVo of(final SignUpRequest request, final Role role) {
        return CreateUserVo.builder()
            .email(request.email())
            .password(request.password())
            .name(request.name())
            .userName(request.userName())
            .phoneNumber(request.phoneNumber())
            .role(role)
            .build();
    }

}
