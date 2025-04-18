package nbc.subjectbaro.domain.common.dto;

import nbc.subjectbaro.domain.user.entity.UserRole;

public record AuthUser(Long id, String username, String nickname, UserRole userRole) {

    public boolean isAdmin() {
        return userRole == UserRole.ADMIN;
    }
}
