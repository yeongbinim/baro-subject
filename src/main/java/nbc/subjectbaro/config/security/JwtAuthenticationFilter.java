package nbc.subjectbaro.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbc.subjectbaro.domain.common.util.JwtUtil;
import nbc.subjectbaro.domain.user.entity.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                String token = jwtUtil.substringToken(bearerToken);
                Claims claims = jwtUtil.extractClaims(token);

                Long userId = Long.valueOf(claims.getSubject());
                String username = claims.get("username", String.class);
                String nickname = claims.get("nickname", String.class);
                UserRole userRole = UserRole.of(claims.get("userRole", String.class));

                JwtUserDetails userDetails = new JwtUserDetails(userId, username, nickname,
                    userRole);

                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.error("JWT 처리 실패: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 인증 실패");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
