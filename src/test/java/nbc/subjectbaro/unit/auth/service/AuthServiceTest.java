package nbc.subjectbaro.unit.auth.service;

import static nbc.subjectbaro.domain.common.exception.ExceptionType.AUTH_CREDENTIALS_INVALID;
import static nbc.subjectbaro.domain.common.exception.ExceptionType.USER_ALREADY_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import nbc.subjectbaro.domain.auth.dto.request.LoginRequest;
import nbc.subjectbaro.domain.auth.dto.request.SignupRequest;
import nbc.subjectbaro.domain.auth.dto.response.LoginResponse;
import nbc.subjectbaro.domain.auth.dto.response.SignupResponse;
import nbc.subjectbaro.domain.auth.service.AuthService;
import nbc.subjectbaro.domain.common.exception.CustomException;
import nbc.subjectbaro.domain.common.util.JwtUtil;
import nbc.subjectbaro.domain.common.util.PasswordEncoder;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.entity.UserRole;
import nbc.subjectbaro.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_success() {
        // given
        SignupRequest req = new SignupRequest("tester", "nick", "123456");
        given(userRepository.existsByUsername("tester")).willReturn(false);
        given(passwordEncoder.encode("123456")).willReturn("hashed");
        given(userRepository.create(any())).willAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        // when
        SignupResponse res = authService.register(req, UserRole.USER);

        // then
        assertThat(res.username()).isEqualTo("tester");
    }

    @Test
    void register_existingUsername_throws() {
        // given
        given(userRepository.existsByUsername("exists")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.register(
            new SignupRequest("exists", "nick", "123456"), UserRole.USER))
            .isInstanceOf(CustomException.class)
            .satisfies(e -> {
                CustomException ex = (CustomException) e;
                assertThat(ex.getCode()).isEqualTo(USER_ALREADY_EXISTS.getCode());
            });
    }

    @Test
    void login_success() {
        // given
        LoginRequest req = new LoginRequest("user", "123456");
        User user = User.builder().id(1L).username("user").nickname("nick")
            .password("hashed").userRole(UserRole.USER).build();

        given(userRepository.findByUsername("user")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("123456", "hashed")).willReturn(true);
        given(jwtUtil.createToken(any(), any(), any(), any())).willReturn("jwt.token");

        // when
        LoginResponse res = authService.login(req);

        // then
        assertThat(res.token()).isEqualTo("jwt.token");
    }

    @Test
    void login_wrongUsername_throws() {
        // given
        given(userRepository.findByUsername("x")).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.login(new LoginRequest("x", "123456")))
            .isInstanceOf(CustomException.class)
            .satisfies(e -> {
                CustomException ex = (CustomException) e;
                assertThat(ex.getCode()).isEqualTo(AUTH_CREDENTIALS_INVALID.getCode());
            });
    }

    @Test
    void login_wrongPassword_throws() {
        // given
        User user = User.builder().username("user").password("hashed").build();
        given(userRepository.findByUsername("user")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("123456", "hashed")).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(new LoginRequest("user", "123456")))
            .isInstanceOf(CustomException.class)
            .satisfies(e -> {
                CustomException ex = (CustomException) e;
                assertThat(ex.getCode()).isEqualTo(AUTH_CREDENTIALS_INVALID.getCode());
            });
    }
}
