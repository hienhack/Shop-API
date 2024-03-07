package com.example.tutorial.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private @NotEmpty String name;

    @Email
    private String email;
}
