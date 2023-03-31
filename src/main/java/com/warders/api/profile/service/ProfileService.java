package com.warders.api.profile.service;

import com.warders.api.profile.controller.dto.ProfileCreationRequest;
import com.warders.api.profile.domain.Profile;
import com.warders.api.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Page<Profile> getProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    public Profile getProfile(final Long profileId) throws NotFoundException {
        return profileRepository.findById(profileId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Profile uploadProfile(final ProfileCreationRequest request, final Long userId) {
        Profile profile = Profile.builder()
            .description(request.description())
            .userId(userId)
            .imageUrl("")
            .subscribeCount(0)
            .build();
        return profileRepository.save(profile);
    }

}
