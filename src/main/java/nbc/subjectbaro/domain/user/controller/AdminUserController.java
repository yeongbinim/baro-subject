package nbc.subjectbaro.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nbc.subjectbaro.domain.user.dto.response.UpdateRoleResponse;
import nbc.subjectbaro.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "관리자 유저 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @Operation(summary = "관리자 권한 부여", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
        responseCode = "200",
        description = "권한 부여 성공",
        content = @Content(schema = @Schema(implementation = UpdateRoleResponse.class))
    )
    @PatchMapping("/{userId}/roles")
    public ResponseEntity<UpdateRoleResponse> giveAdminRole(
        @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.giveAdminRole(userId));
    }
}
