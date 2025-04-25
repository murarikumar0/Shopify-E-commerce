package com.ms.Ecomm.controller;

import com.ms.Ecomm.model.User;
import com.ms.Ecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/users") on the class makes all methods inside this class start with /users.
// it will act as base url
//GET /users → calls getAllUsers()
@RequestMapping("/users")
// * : it will apply for all.
@CrossOrigin("*") // means resource sharing between two different domain
public class UserController {

    // need service here
    @Autowired // automatically inject dependency here.
    private UserService userService;

    // if user wants to register himself.
    @PostMapping("/register")
    public User resgisterUser(@RequestBody User user){

        // here userService will call registerUser method() & will add user details.
        return userService.registerUser(user);
    }

    // feature of login for Users we are providing here.
    @PostMapping("/login")
    public User loginUser(@RequestBody User user){

        return userService.loginUser(user.getEmail() , user.getPassword());
    }


    // APi to get all User Details
    @GetMapping()
    public List<User> getAllUsers()
    {    // calling getAllUsers() method of service layer
        return userService.getAllUsers();
    }


}
