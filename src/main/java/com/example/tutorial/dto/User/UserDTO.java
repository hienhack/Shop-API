package com.example.tutorial.dto.User;

import com.example.tutorial.entity.User;
import com.example.tutorial.enumeration.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String phone;
    private String email;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.email = entity.getEmail();
    }
}
