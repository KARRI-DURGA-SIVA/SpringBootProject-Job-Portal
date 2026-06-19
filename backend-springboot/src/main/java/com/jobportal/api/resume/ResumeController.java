package com.jobportal.api.resume;

import com.jobportal.api.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    private final ResumeRepository resumes;
    private final UserRepository users;

    public ResumeController(ResumeRepository resumes, UserRepository users) {
        this.resumes = resumes;
        this.users = users;
    }

    @GetMapping("/my")
    List<Resume> my(Authentication authentication) {
        return resumes.findByUserEmail(authentication.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Resume create(@Valid @RequestBody ResumeRequest request, Authentication authentication) {
        var user = users.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return resumes.save(new Resume(user, request.fileUrl()));
    }
}
