package com.ms.Ecomm.service;

import com.ms.Ecomm.dto.JwtResponse;
import com.ms.Ecomm.dto.UserResponseDTO;
import com.ms.Ecomm.dto.UserSignUpDTO;
import com.ms.Ecomm.model.User;
import com.ms.Ecomm.repo.UserRepository;
import com.ms.Ecomm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponseDTO registerUser(UserSignUpDTO userSignUpDTO) {
        try
        {
            // check for the duplicate users: if the user email already exists, return bad request
           // Prevents multiple accounts with the same email. & email would be unique
            // Check if user already exists
            Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(userSignUpDTO.getEmail()));
            if (existingUser.isPresent()) {
                throw new RuntimeException("User already exists with this email.");

            }

            User newUser = new User();
            newUser.setName(userSignUpDTO.getName());
            newUser.setEmail(userSignUpDTO.getEmail());
           // first encrypting the pass and then saving to DB
            newUser.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword()));


          User saved =  userRepository.save(newUser); // saving new user to DB

            return UserResponseDTO.builder()
                    .id(saved.getId())
                    .name(saved.getName())
                    .email(saved.getEmail())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }


    }


    public JwtResponse loginUser(String email, String password) {

        // check the user is valid  or not here
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        // token generation
        String token = jwtUtil.generateToken(email);

        // return JwtResponse
        JwtResponse response = new JwtResponse(token);
        return response;
    }
}
