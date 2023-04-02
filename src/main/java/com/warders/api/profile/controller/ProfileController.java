package com.warders.api.profile.controller;

import com.warders.api.common.argumentresolver.annotation.InjectUserId;
import com.warders.api.profile.controller.dto.CommentWriteRequest;
import com.warders.api.profile.controller.dto.ProfileCreationRequest;
import com.warders.api.profile.controller.dto.ProfileDetailResponse;
import com.warders.api.profile.controller.dto.ProfileResponse;
import com.warders.api.profile.controller.dto.ProfileUploadResponse;
import com.warders.api.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "프로필 목록 조회", description = "프로필 목록 조회", tags = { "Profile Controller" })
    @GetMapping
    public Page<ProfileResponse> getProfiles(@PageableDefault Pageable pageable) {
        return profileService.getProfiles(pageable);
    }

    @Operation(summary = "프로필 상세 조회", description = "프로필 상세 조회", tags = { "Profile Controller" })
    @GetMapping("/{id}")
    public ProfileDetailResponse getProfile(@PathVariable("id") Long profileId) {
        return profileService.getProfile(profileId);
    }

    @Operation(summary = "프로필 등록", description = "프로필 등록", tags = { "Profile Controller" })
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProfileUploadResponse uploadProfile(@RequestParam("image") MultipartFile imageFile, ProfileCreationRequest request, @InjectUserId Long userId)
        throws IOException {
        return profileService.uploadProfile(imageFile, request.description(), userId);
    }

    @Operation(summary = "프로필 삭제", description = "프로필 삭제", tags = { "Profile Controller" })
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @DeleteMapping("/{id}")
    public void removeProfile(@PathVariable("id") Long profileId, @InjectUserId Long userId) {
        profileService.removeProfile(profileId, userId);
    }

    @Operation(summary = "댓글 등록", description = "댓글 등록", tags = { "Profile Controller" })
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @PostMapping("/{id}")
    public void writeComment(CommentWriteRequest request, @InjectUserId Long userId,
        @PathVariable("id") Long profileId) {
        profileService.writeComment(profileId, userId, request.content());
    }

}
