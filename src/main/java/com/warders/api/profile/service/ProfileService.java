package com.warders.api.profile.service;

import com.warders.api.image.service.S3UploadService;
import com.warders.api.profile.domain.Profile;
import com.warders.api.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final S3UploadService s3UploadService;

    public Page<Profile> getProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    public Profile getProfile(final Long profileId) throws NotFoundException {
        return profileRepository.findById(profileId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Profile uploadProfile(final MultipartFile imageFile, final String description, final Long userId)
        throws IOException {
        String imageUrl = s3UploadService.upload(imageFile, "images/profiles");

        Profile profile = Profile.builder()
            .description(description)
            .userId(userId)
            .subscribeCount(0)
            .imageUrl(imageUrl)
            .build();

        return profileRepository.save(profile);
    }

}
