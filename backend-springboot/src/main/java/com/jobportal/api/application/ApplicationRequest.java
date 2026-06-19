package com.jobportal.api.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplicationRequest(@NotNull Long jobId, @NotBlank String resumeUrl) {
}
