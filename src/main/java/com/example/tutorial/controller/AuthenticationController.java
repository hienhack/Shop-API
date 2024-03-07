package com.example.tutorial.controller;

import com.example.tutorial.dto.Authentication.CredentialDTO;
import com.example.tutorial.dto.Authentication.PasswordChangeRequestDTO;
import com.example.tutorial.dto.Authentication.ResponseTokenDTO;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.service.AuthenticationService;
import com.example.tutorial.util.AuthenticationHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/sign-in")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<ResponseTokenDTO> authenticate(@RequestBody @Valid CredentialDTO authRequest) {
        return ResponseDTO.of(authenticationService.authenticate(authRequest));
    }

    @PostMapping(value = "/change-password")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void changePassword(Authentication authentication, @RequestBody @Valid PasswordChangeRequestDTO request) {
        User user = AuthenticationHelper.getUser(authentication);
        authenticationService.changePassword(user, request);
    }

}
