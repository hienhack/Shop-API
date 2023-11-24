package com.example.tutorial.service;

import com.example.tutorial.dto.Authentication.CredentialDTO;
import com.example.tutorial.dto.Authentication.ResponseTokenDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.repository.UserRepository;
import com.example.tutorial.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseTokenDTO authenticate(CredentialDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // If the code can reach here then there is no exception to find out the user
        User user = userRepository.findUserByAccount(request.getUsername()).orElseThrow();
        String token = jwtTokenProvider.generateToken(user);

        return new ResponseTokenDTO(token, token);
    }
}
