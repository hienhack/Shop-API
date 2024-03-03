package com.example.tutorial.config;

import com.example.tutorial.util.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
//            auth.requestMatchers(
//                    "/api/auth/**"
//            ).permitAll();
//
//            auth.requestMatchers(HttpMethod.GET,
//                    "/api/category/**"
//            ).permitAll();
//
//            auth.requestMatchers(HttpMethod.POST, "/api/users").permitAll();
//
//            auth.requestMatchers(HttpMethod.POST,
//                    "/api/products"
//            ).permitAll();


            auth.anyRequest().permitAll();

//            auth.anyRequest().authenticated();
        });

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider);

        return http.build();
    }

    /*
     * admin:
     * create new account for other admins
     * create, update, delete product
     * get list of users
     *
     * user:
     * get products
     * get product
     * put their own user
     *
     *
     */
}
