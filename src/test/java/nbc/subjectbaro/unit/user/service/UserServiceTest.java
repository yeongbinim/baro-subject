package nbc.subjectbaro.unit.user.service;

import static nbc.subjectbaro.domain.common.exception.ExceptionType.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import nbc.subjectbaro.domain.common.exception.CustomException;
import nbc.subjectbaro.domain.user.dto.response.UpdateRoleResponse;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.entity.UserRole;
import nbc.subjectbaro.domain.user.repository.UserRepository;
import nbc.subjectbaro.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void giveAdminRole_success() {
        // given
        User user = User.builder()
            .id(1L)
            .username("test")
            .userRole(UserRole.USER)
            .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // when
        UpdateRoleResponse response = userService.giveAdminRole(1L);

        // then
        assertThat(response.roles().get(0).role()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void giveAdminRole_userNotFound() {
        // given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.giveAdminRole(1L))
            .isInstanceOf(CustomException.class)
            .satisfies(e -> {
                CustomException ex = (CustomException) e;
                assertThat(ex.getCode()).isEqualTo(USER_NOT_FOUND.getCode());
            });
    }
}
