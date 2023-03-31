package com.warders.api.profile.controller;

import com.warders.api.common.argumentresolver.annotation.InjectUserId;
import com.warders.api.profile.controller.dto.ProfileCreationRequest;
import com.warders.api.profile.domain.Profile;
import com.warders.api.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "프로필 목록 조회", description = "프로필 목록 조회", tags = { "Profile Controller" })
    @GetMapping
    public Page<Profile> getProfiles(@PageableDefault Pageable pageable) {
        return profileService.getProfiles(pageable);
    }

    @Operation(summary = "프로필 상세 조회", description = "프로필 상세 조회", tags = { "Profile Controller" })
    @GetMapping("/{id}")
    public Profile getProfile(@PathVariable("id") Long profileId) throws NotFoundException {
        return profileService.getProfile(profileId);
    }

    @Operation(summary = "프로필 등록", description = "프로필 등록", tags = { "Profile Controller" })
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @PostMapping
    public Profile uploadProfile(@RequestBody @Valid final ProfileCreationRequest request, @Parameter(hidden = true) @InjectUserId final Long userId) {
        return profileService.uploadProfile(request, userId);
    }
}
