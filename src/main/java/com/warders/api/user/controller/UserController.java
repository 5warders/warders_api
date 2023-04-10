package com.warders.api.user.controller;

import com.warders.api.user.service.AuthService;
import com.warders.api.common.argumentresolver.annotation.InjectUserId;
import com.warders.api.user.controller.dto.SignInRequest;
import com.warders.api.user.controller.dto.SignInResponse;
import com.warders.api.user.controller.dto.SignUpRequest;
import com.warders.api.user.domain.Role;
import com.warders.api.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입", tags = { "Auth Controller" })
    @PostMapping("/signup")
    public User signup(@RequestBody @Valid SignUpRequest request) {
        return authService.signup(request, Role.ROLE_USER);
    }

    @Operation(summary = "로그인", description = "로그인", tags = { "Auth Controller" })
    @PostMapping("/login")
    public SignInResponse login(@RequestBody @Valid SignInRequest request) {
        return new SignInResponse(authService.login(request));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴", tags = { "Auth Controller" })
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @DeleteMapping("/withdrawal")
    public void withdraw(@InjectUserId Long userId) {
        authService.withdraw(userId);
    }

}
