package com.warders.api.auth.controller;

import com.warders.api.auth.controller.dto.SignInRequest;
import com.warders.api.auth.controller.dto.SignUpRequest;
import com.warders.api.auth.service.AuthService;
import com.warders.api.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입", tags = { "Auth Controller" })
    @PostMapping("/signup")
    public User signup(@RequestBody @Valid SignUpRequest request) {
        return authService.signup(request);
    }

    @Operation(summary = "로그인", description = "로그인", tags = { "Auth Controller" })
    @PostMapping("/login")
    public String login(@RequestBody @Valid SignInRequest request) {
        return authService.login(request);
    }

}
