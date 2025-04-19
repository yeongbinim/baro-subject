package nbc.subjectbaro.config.security;

import nbc.subjectbaro.domain.user.entity.UserRole;

public record JwtUserDetails(Long userId, String username, String nickname, UserRole userRole) {

    public boolean isAdmin() {
        return userRole == UserRole.ADMIN;
    }
}
