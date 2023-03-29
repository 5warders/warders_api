package com.warders.api.user.service;

import com.warders.api.common.exception.error.ErrorCode;
import com.warders.api.user.domain.Role;
import com.warders.api.user.domain.User;
import com.warders.api.user.repository.UserRepository;
import com.warders.api.user.service.vo.CreateUserVo;
import java.util.InputMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    public User createUser(final CreateUserVo createUserVo) {
        if (userRepository.existsByEmail(createUserVo.getEmail())) {
            throw new DuplicateKeyException(ErrorCode.USER_DUPLICATE.getMessage());
        }

        final User user = User.builder()
            .email(createUserVo.getEmail())
            .name(createUserVo.getName())
            .nickname(createUserVo.getNickname())
            .password(bCryptPasswordEncoder.encode(createUserVo.getPassword()))
            .phoneNumber(createUserVo.getPhoneNumber())
            .role(Role.ROLE_USER)
            .build();

        return userRepository.save(user);
    }

    public void validatePassword(final String inputPassword, final String password) {
        boolean matches = bCryptPasswordEncoder.matches(inputPassword, password);
        if (!matches) {
            throw new InputMismatchException(ErrorCode.PASSWORD_MISMATCH.getMessage());
        }
    }

}
