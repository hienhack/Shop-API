package com.example.tutorial.controller;

import com.example.tutorial.dto.Address.AddressDTO;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.service.AddressService;
import com.example.tutorial.util.AuthenticationHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<List<AddressDTO>> getAll(Authentication authentication) {
        User user = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(addressService.getAll(user));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<AddressDTO> addAddress(Authentication authentication, @RequestBody @Valid AddressDTO addressDTO) {
        User user = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(addressService.create(user, addressDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<AddressDTO> update(
            Principal principal,
            @PathVariable(name = "id") Integer id,
            @RequestBody @Valid AddressDTO addressDTO
    ) {
        User user = (User)principal;
        return ResponseDTO.of(addressService.update(user, id, addressDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(Principal principal, @PathVariable(name = "id") Integer id) {
        User user = (User)principal;
        addressService.delete(user, id);
    }
}
