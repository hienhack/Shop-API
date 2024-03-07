package com.example.tutorial.dto.Authentication;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CredentialDTO {
    private @NotEmpty String username;
    private @NotEmpty String password;
}
