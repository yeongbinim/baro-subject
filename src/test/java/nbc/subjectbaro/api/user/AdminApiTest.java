package nbc.subjectbaro.api.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nbc.subjectbaro.domain.common.util.JwtUtil;
import nbc.subjectbaro.domain.user.entity.User;
import nbc.subjectbaro.domain.user.entity.UserRole;
import nbc.subjectbaro.domain.user.repository.MemoryUserRepository;
import nbc.subjectbaro.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AdminApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @BeforeEach
    void clearRepo() {
        if (userRepository instanceof MemoryUserRepository memoryRepo) {
            memoryRepo.clear();
        }
    }

    private String getToken(User user) {
        return jwtUtil.createToken(
            user.getId(),
            user.getUsername(),
            user.getNickname(),
            user.getUserRole()
        );
    }

    @Test
    @DisplayName("관리자는 성공적으로 사용자의 권한을 변경할 수 있다.")
    void giveAdminRole_success_byAdmin() throws Exception {
        User admin = userRepository.create(User.builder()
            .username("admin").nickname("admin").password("123456")
            .userRole(UserRole.ADMIN).build());

        User target = userRepository.create(User.builder()
            .username("target").nickname("target").password("123456")
            .userRole(UserRole.USER).build());

        String token = getToken(admin);

        mockMvc.perform(patch("/admin/users/" + target.getId() + "/roles")
                .header("Authorization", token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.roles[0].role").value("ADMIN"));
    }

    @Test
    @DisplayName("사용자는 관리자가 아니면 권한을 변경할 수 없다.")
    void giveAdminRole_forbidden_ifNotAdmin() throws Exception {
        User user = userRepository.create(User.builder()
            .username("user").nickname("u").password("123456")
            .userRole(UserRole.USER).build());

        User target = userRepository.create(User.builder()
            .username("target").nickname("t").password("123456")
            .userRole(UserRole.USER).build());

        String token = getToken(user);

        mockMvc.perform(patch("/admin/users/" + target.getId() + "/roles")
                .header("Authorization", token))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error.code").value("ACCESS_DENIED"));
    }

    @Test
    @DisplayName("관리자는 존재하지 않는 유저의 권한을 변경할 수 없다.")
    void giveAdminRole_userNotFound() throws Exception {
        User admin = userRepository.create(User.builder()
            .username("admin").nickname("a").password("123456")
            .userRole(UserRole.ADMIN).build());

        String token = getToken(admin);

        mockMvc.perform(patch("/admin/users/9999/roles")
                .header("Authorization", token))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"));
    }
}
