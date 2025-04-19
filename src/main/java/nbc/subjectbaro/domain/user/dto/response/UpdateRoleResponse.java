package nbc.subjectbaro.domain.user.dto.response;

import java.util.List;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.entity.UserRole;

public record UpdateRoleResponse(
    String username,
    String nickname,
    List<RoleResponse> roles
) {

    public static UpdateRoleResponse from(User user) {
        return new UpdateRoleResponse(
            user.getUsername(),
            user.getNickname(),
            List.of(new RoleResponse(user.getUserRole()))
        );
    }

    public record RoleResponse(
        UserRole role
    ) {

    }
}
