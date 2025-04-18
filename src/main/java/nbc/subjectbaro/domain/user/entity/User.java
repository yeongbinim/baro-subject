package nbc.subjectbaro.domain.user.entity;

import static nbc.subjectbaro.domain.common.exception.ExceptionType.USER_ALREADY_ADMIN;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nbc.subjectbaro.domain.common.exception.CustomException;

@Getter
public class User {

    @Setter
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private UserRole userRole;

    @Builder
    public User(
        Long id,
        String username,
        String password,
        String nickname,
        UserRole userRole
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public void giveAdminRole() {
        if (userRole == UserRole.ADMIN) {
            throw new CustomException(USER_ALREADY_ADMIN);
        }
        this.userRole = UserRole.ADMIN;
    }
}
