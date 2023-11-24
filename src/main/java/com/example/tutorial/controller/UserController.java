package com.example.tutorial.controller;

import com.example.tutorial.dto.User.UserCreationDTO;
import com.example.tutorial.dto.User.UserDTO;
import com.example.tutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public UserDTO getUser(@RequestParam(name = "id") Integer id) {
        return new UserDTO(userService.loadUserById(id));
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserCreationDTO userCreationDTO) {
        return userService.create(userCreationDTO);
    }

    @PutMapping(value = "/{userId}/addresses/{addressId}")
    public String updateAddress(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "addressId") Integer addressId
    ) {

        return "updated successfully";
    }
}
