package com.example.tutorial.controller;

import com.example.tutorial.dto.Authentication.CredentialDTO;
import com.example.tutorial.dto.Authentication.ResponseTokenDTO;
import com.example.tutorial.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<ResponseTokenDTO> authenticate(@RequestBody CredentialDTO authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}
