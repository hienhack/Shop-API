package com.example.tutorial.service;

import com.example.tutorial.dto.Authentication.CredentialDTO;
import com.example.tutorial.dto.Authentication.PasswordChangeRequestDTO;
import com.example.tutorial.dto.Authentication.ResponseTokenDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.UserRepository;
import com.example.tutorial.util.JwtTokenProvider;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseTokenDTO authenticate(CredentialDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BusinessException("Username or password is not correct");
        }

        User user = userRepository.findUserByAccount(request.getUsername()).orElseThrow();
        String token = jwtTokenProvider.generateToken(user);

        return new ResponseTokenDTO(token, token);
    }

    public void logout(User user) {

    }

    public void changePassword(User user, PasswordChangeRequestDTO request) {
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        }
    }

    public void sendPasswordResetMail(User user) {
        // Todo: send an mail including password reset url
    }

    public void sendPasswordResetSMS(User user) {
        // Todo: send an SMS including new verification code
    }
}
