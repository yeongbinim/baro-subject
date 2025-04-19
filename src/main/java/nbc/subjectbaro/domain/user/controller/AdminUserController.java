package nbc.subjectbaro.domain.user.controller;

import lombok.RequiredArgsConstructor;
import nbc.subjectbaro.domain.common.annotation.AdminCheck;
import nbc.subjectbaro.domain.user.dto.response.UpdateRoleResponse;
import nbc.subjectbaro.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @AdminCheck
    @PatchMapping("/{userId}/roles")
    public ResponseEntity<UpdateRoleResponse> giveAdminRole(
        @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.giveAdminRole(userId));
    }
}
