package com.example.tutorial.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(
                    HttpMethod.GET,
                    "/api/users",
                    "/api/users/profile/{id}",
                    "/api/report/**"
            ).hasRole("ADMIN_READ");

            auth.requestMatchers(
                    HttpMethod.POST,
                    "/api/products",
                    "/api/categories",
                    "/api/users/admins"
            ).hasRole("ADMIN_CREATE");

            auth.requestMatchers(
                    HttpMethod.DELETE,
                    "/api/products/**",
                    "/api/categories/**",
                    "api/users/**"
            ).hasRole("ADMIN_CREATE");

            auth.requestMatchers(
                    HttpMethod.PUT,
                    "/api/products/*",
                    "/api/categories/*"
            ).hasRole("ADMIN_UPDATE");

            auth.requestMatchers(
                    HttpMethod.GET,
                    "/api/products/**",
                    "/api/category/**",
                    "/api/auth/sign-in"
            ).permitAll();

            auth.requestMatchers("/api/images/**").permitAll();

            auth.anyRequest().authenticated();
        });

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider);

        return http.build();
    }
}
