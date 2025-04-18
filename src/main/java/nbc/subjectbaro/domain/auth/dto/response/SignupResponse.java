package nbc.subjectbaro.domain.auth.dto.response;

import java.util.List;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.entity.UserRole;

public record SignupResponse(
    String username,
    String nickname,
    List<RoleResponse> roles
) {

    public static SignupResponse from(User user) {
        return new SignupResponse(
            user.getUsername(),
            user.getNickname(),
            List.of(new RoleResponse(user.getUserRole()))
        );
    }

    private record RoleResponse(
        UserRole role
    ) {

    }
}
