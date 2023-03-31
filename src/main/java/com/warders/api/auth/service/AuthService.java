package com.warders.api.auth.service;

import com.warders.api.auth.controller.dto.SignInRequest;
import com.warders.api.auth.controller.dto.SignUpRequest;
import com.warders.api.common.component.jwt.JwtTokenProvider;
import com.warders.api.user.domain.User;
import com.warders.api.user.service.UserService;
import com.warders.api.user.service.vo.CreateUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public User signup(final SignUpRequest request) {
        return userService.createUser(CreateUserVo.of(request));
    }

    public String login(final SignInRequest request) {
        final User user = userService.getUserByEmail(request.email());

        userService.validatePassword(request.password(), user.getPassword());
        return tokenProvider.generateToken(user.getUserId().toString(), user.getRole());
    }

}
