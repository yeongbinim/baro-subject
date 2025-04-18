package nbc.subjectbaro.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
    @NotBlank
    @Size(max = 20)
    String username,

    @NotBlank
    @Size(max = 20)
    String nickname,

    @NotBlank
    @Size(min = 6, max = 20)
    String password
) {

}
