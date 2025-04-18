package nbc.subjectbaro.domain.user.entity;

import static nbc.subjectbaro.domain.common.exception.ExceptionType.USER_ROLE_INVALID;

import java.util.Arrays;
import nbc.subjectbaro.domain.common.exception.CustomException;

public enum UserRole {
    ADMIN, USER;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
            .filter(r -> r.name().equalsIgnoreCase(role))
            .findFirst()
            .orElseThrow(() -> new CustomException(USER_ROLE_INVALID));
    }
}
