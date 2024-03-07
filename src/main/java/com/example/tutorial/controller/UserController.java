package com.example.tutorial.controller;

import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.dto.User.UserCreationDTO;
import com.example.tutorial.dto.User.UserDTO;
import com.example.tutorial.dto.User.UserUpdateDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.service.UserService;
import com.example.tutorial.util.AuthenticationHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Used for admin to get user profile
     * @param id user id
     * @return user information
     */
    @GetMapping(value = "/profile/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<UserDTO> getUser(@PathVariable(name = "id") Integer id) {
        return ResponseDTO.of(userService.getUser(id));
    }

    /**
     * Used for normal users to view their profile
     * @param principal logged in user
     * @return user information
     */
    @GetMapping(value = "/profile")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<UserDTO> getUser(Principal principal) {
        User user = (User)principal;
        return ResponseDTO.of(userService.getUser(user.getId()));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<List<UserDTO>> getAllUsers() {
        return ResponseDTO.of(userService.getAllUsers());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<UserDTO> createUser(@RequestBody @Valid UserCreationDTO userCreationDTO) {
        return ResponseDTO.of(userService.create(userCreationDTO));
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<UserDTO> updateUser(Authentication authentication, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        User user = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(userService.updateUser(user, userUpdateDTO));
    }
}
