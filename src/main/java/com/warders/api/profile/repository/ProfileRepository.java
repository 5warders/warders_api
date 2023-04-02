package com.warders.api.profile.repository;

import com.warders.api.profile.domain.Profile;
import com.warders.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    void deleteByProfileIdAndUser(Long profileId, User user);

    boolean existsByProfileIdAndUser(Long profileId, User user);

}
