package com.ms.Ecomm.controller;


import com.ms.Ecomm.dto.JwtResponse;
import com.ms.Ecomm.dto.UserResponseDTO;
import com.ms.Ecomm.dto.UserSignInDTO;
import com.ms.Ecomm.dto.UserSignUpDTO;
import com.ms.Ecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;



    // register/signup user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUpDTO userSignUpDTO)
    {
        try{
            UserResponseDTO response =  userService.registerUser(userSignUpDTO);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            e.printStackTrace(); //  log exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    // login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserSignInDTO userSignInDTO) {
        try {
                JwtResponse response = userService.loginUser(userSignInDTO.getEmail(), userSignInDTO.getPassword());
                return ResponseEntity.ok(response);
        }catch (Exception e) {
            e.printStackTrace(); //  log exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}


// pass
// ram -> ram@321
//vinay -> @vinay321
//abhi -> @abhi321