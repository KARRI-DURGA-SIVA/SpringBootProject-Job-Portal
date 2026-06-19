package com.jobportal.api.resume;

import com.jobportal.api.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "file_url", nullable = false)
    @NotBlank
    private String fileUrl;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt = Instant.now();

    protected Resume() {
    }

    public Resume(User user, String fileUrl) {
        this.user = user;
        this.fileUrl = fileUrl;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getFileUrl() { return fileUrl; }
    public Instant getUploadedAt() { return uploadedAt; }
}
