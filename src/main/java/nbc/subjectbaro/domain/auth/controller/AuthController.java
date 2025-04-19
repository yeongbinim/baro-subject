package nbc.subjectbaro.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Auth", description = "로그인 회원가입")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "일반 회원가입")
    @ApiResponse(
        responseCode = "201",
        description = "회원가입 성공",
        content = @Content(schema = @Schema(implementation = SignupResponse.class))
    )
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
        @Valid @RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.register(signupRequest, UserRole.USER));
    }

    @Operation(summary = "ADMIN 회원가입")
    @ApiResponse(
        responseCode = "201",
        description = "회원가입 성공",
        content = @Content(schema = @Schema(implementation = SignupResponse.class))
    )
    @PostMapping("/admin-signup")
    public ResponseEntity<SignupResponse> adminSignup(
        @Valid @RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.register(signupRequest, UserRole.ADMIN));
    }

    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
        content = @Content(schema = @Schema(implementation = LoginResponse.class))
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(authService.login(loginRequest));
    }
}
