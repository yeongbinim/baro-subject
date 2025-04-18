package nbc.subjectbaro.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.subjectbaro.domain.auth.dto.request.LoginRequest;
import nbc.subjectbaro.domain.auth.dto.request.SignupRequest;
import nbc.subjectbaro.domain.auth.dto.response.LoginResponse;
import nbc.subjectbaro.domain.auth.dto.response.SignupResponse;
import nbc.subjectbaro.domain.auth.service.AuthService;
import nbc.subjectbaro.domain.user.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
        @Valid @RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.register(signupRequest, UserRole.USER));
    }

    @PostMapping("/admin-signup")
    public ResponseEntity<SignupResponse> adminSignup(
        @Valid @RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.register(signupRequest, UserRole.ADMIN));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(authService.login(loginRequest));
    }
}
