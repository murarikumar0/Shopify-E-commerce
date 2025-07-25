package com.ms.Ecomm.config;

import com.ms.Ecomm.service.CustomUserDetailsServices;
import com.ms.Ecomm.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/login", "/users/register").permitAll()
                        .anyRequest().authenticated()
                )
                //this tells security to Do not use HTTP sessions to store the authentication state , make app stateless
                //JWT is stateless: the token itself contains all the information. Once the server verifies the token, it doesn't need to remember anything in a session.
                //No server-side memory or session storage is needed — everything comes from the token attached to each request.
                //NOTE : without this Spring Security will create a session after login and track the user using that session.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Make sure in your SecurityConfig, you register the filter before UsernamePasswordAuthenticationFilter
        //This ensures JWT processing happens before Spring Security tries to enforce authentication
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
