package nbc.subjectbaro.config;

import static nbc.subjectbaro.domain.common.exception.ExceptionType.USER_ACCESS_DENIED;

import nbc.subjectbaro.domain.common.dto.AuthUser;
import nbc.subjectbaro.domain.common.exception.CustomException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthCheckAspect {

    @Before("@annotation(nbc.subjectbaro.domain.common.annotation.AdminCheck) && args(authUser, ..)")
    public void adminCheck(AuthUser authUser) {
        if (!authUser.isAdmin()) {
            throw new CustomException(USER_ACCESS_DENIED);
        }
    }
}
