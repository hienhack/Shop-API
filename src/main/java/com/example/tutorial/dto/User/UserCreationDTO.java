package com.example.tutorial.dto.User;

import com.example.tutorial.entity.UserRole;
import com.example.tutorial.enumeration.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserCreationDTO {
    private @NotEmpty String name;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Phone number must have 10 characters")
    private String phone;

    @NotEmpty
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @Email
    private String email;

    @NotNull
    private @Valid List<UserRole> roles;

    public void setRole(Set<String> roles) {
        this.roles = roles.stream().map(role ->
                new UserRole(Role.valueOf(role))).toList();
    }
}
