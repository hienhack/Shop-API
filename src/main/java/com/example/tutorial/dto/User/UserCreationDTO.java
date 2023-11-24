package com.example.tutorial.dto.User;

import com.example.tutorial.entity.UserRole;
import com.example.tutorial.enumeration.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserCreationDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private List<UserRole> roles;

    public void setRole(Set<String> roles) {
        this.roles = roles.stream().map(role ->
                new UserRole(Role.valueOf(role))).toList();
    }
}
