package com.jobportal.api.auth;

import com.jobportal.api.user.Role;

public record AuthResponse(String token, Long id, String name, String email, Role role) {
}
