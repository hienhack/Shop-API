package com.example.tutorial.dto.Authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordChangeRequestDTO {
    @JsonProperty("old_password")
    private @NotEmpty String oldPassword;

    @NotEmpty
    @Min(value = 8)
    @JsonProperty("new_password")
    private String newPassword;
}
