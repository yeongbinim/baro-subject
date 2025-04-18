package nbc.subjectbaro.domain.common.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    //Server
    SERVER_AUTH_WRONG_USED(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_AUTH_WRONG_USED",
        "@Auth와 AuthUser 타입은 함께 사용되어야 합니다."),

    //Auth
    AUTH_CREDENTIALS_INVALID(HttpStatus.BAD_REQUEST, "INVALID_CREDENTIALS",
        "아이디 또는 비밀번호가 올바르지 않습니다."),
    AUTH_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN_NOT_FOUND", "토큰을 찾을 수 없습니다."),

    //User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER_ALREADY_EXISTS", "이미 가입된 사용자입니다."),
    USER_ROLE_INVALID(HttpStatus.BAD_REQUEST, "USER_ROLE_INVALID", "유효하지 않은 UserRole 입니다."),
    USER_ALREADY_ADMIN(HttpStatus.BAD_REQUEST, "USER_ALREADY_ADMIN", "이미 Admin 사용자입니다."),
    USER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "접근 권한이 없습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
