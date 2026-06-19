package com.jobportal.api.resume;

import jakarta.validation.constraints.NotBlank;

public record ResumeRequest(@NotBlank String fileUrl) {
}
