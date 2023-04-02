package com.warders.api.profile.service;

import com.warders.api.image.service.S3UploadService;
import com.warders.api.profile.controller.dto.ProfileDetailResponse;
import com.warders.api.profile.controller.dto.ProfileResponse;
import com.warders.api.profile.controller.dto.ProfileUploadResponse;
import com.warders.api.profile.domain.Comment;
import com.warders.api.profile.domain.Profile;
import com.warders.api.profile.domain.mapper.ProfileDtoMapper;
import com.warders.api.profile.repository.CommentRepository;
import com.warders.api.profile.repository.ProfileRepository;
import com.warders.api.user.domain.User;
import com.warders.api.user.service.UserService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;
    private final S3UploadService s3UploadService;

    private final ProfileRepository profileRepository;
    private final CommentRepository commentRepository;
    private final ProfileDtoMapper profileDtoMapper;

    public Page<ProfileResponse> getProfiles(Pageable pageable) {
        Page<Profile> profiles = profileRepository.findAll(pageable);
        return profiles.map(profileDtoMapper::toResponse);
    }

    public ProfileDetailResponse getProfile(final Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        return profileDtoMapper.toDetailResponse(profile);
    }

    @Transactional
    public ProfileUploadResponse uploadProfile(final MultipartFile imageFile, final String description, final Long userId)
        throws IOException {
        String imageUrl = s3UploadService.upload(imageFile, "images/profiles");

        Profile profile = Profile.builder()
            .description(description)
            .user(userService.getUser(userId))
            .subscribeCount(0)
            .imageUrl(imageUrl)
            .build();

        profileRepository.save(profile);

        return new ProfileUploadResponse(imageUrl);
    }

    @Transactional
    public void removeProfile(final Long profileId, final Long userId) {
        User user = userService.getUser(userId);

        if (!profileRepository.existsByProfileIdAndUser(profileId, user)) {
            throw new NotFoundException("해당 사용자에게 존재하지 않는 프로필 입니다.");
        }
        profileRepository.deleteByProfileIdAndUser(profileId, user);
    }

    @Transactional
    public void removeProfile(final Long profileId) {
        if (!profileRepository.existsById(profileId)) {
            throw new NotFoundException("존재하지 않는 프로필 입니다.");
        }
        profileRepository.deleteById(profileId);
    }

    public void writeComment(final Long profileId, final Long writeUserId, final String content) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();

        Comment comment = Comment.builder()
            .profile(profile)
            .content(content)
            .user(userService.getUser(writeUserId))
            .build();
        commentRepository.save(comment);
    }

}
