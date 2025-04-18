package nbc.subjectbaro.domain.auth.service;

import lombok.RequiredArgsConstructor;
import nbc.subjectbaro.domain.auth.dto.request.SignupRequest;
import nbc.subjectbaro.domain.auth.dto.response.SignupResponse;
import nbc.subjectbaro.domain.common.exception.CustomException;
import nbc.subjectbaro.domain.common.exception.ExceptionType;
import nbc.subjectbaro.domain.common.util.PasswordEncoder;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.entity.UserRole;
import nbc.subjectbaro.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse register(SignupRequest signupRequest, UserRole userRole) {
        if (userRepository.existsByUsername(signupRequest.username())) {
            throw new CustomException(ExceptionType.USER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.password());

        User user = User.builder()
            .username(signupRequest.username())
            .nickname(signupRequest.nickname())
            .password(encodedPassword)
            .userRole(userRole)
            .build();

        User savedUser = userRepository.create(user);

        return SignupResponse.from(savedUser);
    }
}
