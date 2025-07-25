package com.ms.Ecomm.util;

import com.ms.Ecomm.service.CustomUserDetailsServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter {



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;

    //This method is called for every request, because the class extends OncePerRequestFilter.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //This reads the Authorization header from the incoming HTTP request.
        // Step 1: Get the Authorization Header
        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Removes "Bearer " prefix

            //it extracts the token and then extracts the email (subject) from the token using JwtUtil.
            email = jwtUtil.extractEmail(token);
        }

        // Step 3: Check if Already Authenticated
        //This avoids re-authenticating if the user is already logged in for the current request.
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //Loads user details from the database.
            UserDetails userDetails = customUserDetailsServices.loadUserByUsername(email);

            //Validates the token using your JwtUtil.
            //If valid: set user as authenticated.
            //1 Creates a Spring Security Authentication object.
            //2.Sets it in the SecurityContextHolder, which makes the user authenticated for the rest of the request.
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                // creating auth object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // setting into context holder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //After your custom logic, control passes to the next filter in the chain (could be Spring MVC, another filter, etc.).
        filterChain.doFilter(request, response);
    }
}
