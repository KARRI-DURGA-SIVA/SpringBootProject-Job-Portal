package com.jobportal.api.auth;

import com.jobportal.api.user.User;
import com.jobportal.api.user.UserRepository;
import com.jobportal.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UserRepository users,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    AuthResponse register(@Valid @RequestBody AuthRequest request) {
        if (users.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        User user = users.save(new User(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.role()
        ));
        return response(user);
    }

    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = users.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        return response(user);
    }

    @PostMapping("/google")
    AuthResponse googlePlaceholder() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Google login needs OAuth client configuration");
    }

    private AuthResponse response(User user) {
        return new AuthResponse(jwtService.generateToken(user), user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
