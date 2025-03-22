package clinic.api.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserPersistData(@NotBlank String login, @NotBlank String password) {}
