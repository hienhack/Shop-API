package com.example.tutorial.util;

import com.example.tutorial.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationHelper {
    public static User getUser(Authentication authentication) {
        var authenticated = (UsernamePasswordAuthenticationToken) authentication;
        return (User) authenticated.getPrincipal();
    }
}
