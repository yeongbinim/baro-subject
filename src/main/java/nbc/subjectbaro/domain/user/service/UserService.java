package nbc.subjectbaro.domain.user.service;

import static nbc.subjectbaro.domain.common.exception.ExceptionType.USER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import nbc.subjectbaro.domain.common.exception.CustomException;
import nbc.subjectbaro.domain.user.dto.response.UpdateRoleResponse;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UpdateRoleResponse giveAdminRole(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        user.giveAdminRole();

        return UpdateRoleResponse.from(user);
    }
}
