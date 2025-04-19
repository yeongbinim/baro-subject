package nbc.subjectbaro.api.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nbc.subjectbaro.domain.auth.dto.request.LoginRequest;
import nbc.subjectbaro.domain.auth.dto.request.SignupRequest;
import nbc.subjectbaro.domain.common.util.PasswordEncoder;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void clearRepo() {
        if (userRepository instanceof MemoryUserRepository memoryRepo) {
            memoryRepo.clear();
        }
    }

    @Test
    @DisplayName("사용자는 회원가입을 할 수 있다.")
    void signup_success() throws Exception {
        SignupRequest req = new SignupRequest("tester", "nick", "123456");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value("tester"));
    }

    @Test
    @DisplayName("사용자는 중복된 회원가입을 할 수 없다.")
    void signup_duplicateUsername() throws Exception {
        userRepository.create(User.builder()
            .username("tester").nickname("exist").password("123456").userRole(UserRole.USER)
            .build());

        SignupRequest req = new SignupRequest("tester", "nick", "123456");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error.code").value("USER_ALREADY_EXISTS"));
    }

    @Test
    @DisplayName("사용자는 로그인 할 수 있다.")
    void login_success() throws Exception {
        // 회원가입
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignupRequest("tester", "nick", "123456")
                )))
            .andExpect(status().isCreated());

        // 로그인
        LoginRequest loginReq = new LoginRequest("tester", "123456");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginReq)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("사용자는 잘못된 정보로 로그인 할 수 없다.")
    void login_invalidPassword() throws Exception {
        userRepository.create(User.builder()
            .username("tester").nickname("nick")
            .password(passwordEncoder.encode("123456"))
            .userRole(UserRole.USER).build());

        LoginRequest req = new LoginRequest("tester", "wrong");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error.code").value("INVALID_CREDENTIALS"));
    }
}
