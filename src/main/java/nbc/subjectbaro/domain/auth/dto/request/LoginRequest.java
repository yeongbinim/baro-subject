package nbc.subjectbaro.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank
    String username,

    @NotBlank
    String password
) {

}
