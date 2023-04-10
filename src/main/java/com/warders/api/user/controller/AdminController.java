package com.warders.api.user.controller;

import com.warders.api.user.service.AuthService;
import com.warders.api.user.controller.dto.SignUpRequest;
import com.warders.api.user.domain.Role;
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
@RequestMapping("/v1/admin")
public class AdminController {

    private final AuthService authService;

    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입", tags = { "Admin Auth Controller" })
    @PostMapping("/signup")
    public User signup(@RequestBody @Valid SignUpRequest request) {
        return authService.signup(request, Role.ROLE_ADMIN);
    }

}
