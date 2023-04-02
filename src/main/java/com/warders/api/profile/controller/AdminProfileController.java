package com.warders.api.profile.controller;

import com.warders.api.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/admin/profiles")
public class AdminProfileController {

    private final ProfileService profileService;

    @Operation(summary = "관리자 프로필 삭제", description = "관리자 프로필 삭제", tags = { "Admin Profile Controller" })
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @DeleteMapping("/{id}")
    public void removeProfile(@PathVariable("id") Long profileId) {
        profileService.removeProfile(profileId);
    }

}
