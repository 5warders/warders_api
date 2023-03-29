package com.warders.api.auth.service;

import com.warders.api.common.exception.error.ErrorCode;
import com.warders.api.user.domain.User;
import com.warders.api.user.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String userPk) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(userPk))
            .map(this::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException(userPk + ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    private UserDetails createUserDetails(final User user) {
        final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());

        return new org.springframework.security.core.userdetails.User(
            String.valueOf(user.getSeq()),
            user.getPassword(),
            Collections.singleton(grantedAuthority)
        );
    }
}
