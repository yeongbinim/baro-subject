package nbc.subjectbaro.config;

import static nbc.subjectbaro.domain.common.exception.ExceptionType.USER_ACCESS_DENIED;

import lombok.extern.slf4j.Slf4j;
import nbc.subjectbaro.config.security.JwtUserDetails;
import nbc.subjectbaro.domain.common.exception.CustomException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AdminCheckAspect {

    @Before("@annotation(nbc.subjectbaro.domain.common.annotation.AdminCheck)")
    public void checkAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
            || !(authentication.getPrincipal() instanceof JwtUserDetails userDetails)) {
            throw new CustomException(USER_ACCESS_DENIED);
        }

        if (!userDetails.isAdmin()) {
            throw new CustomException(USER_ACCESS_DENIED);
        }
    }
}
