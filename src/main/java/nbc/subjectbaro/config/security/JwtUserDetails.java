package nbc.subjectbaro.config.security;

import java.util.Collection;
import java.util.List;
import nbc.subjectbaro.domain.user.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public record JwtUserDetails(Long userId, String username, String nickname, UserRole userRole) {

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }
}
