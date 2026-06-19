package com.jobportal.api.auth;

import com.jobportal.api.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotNull Role role
) {
}
