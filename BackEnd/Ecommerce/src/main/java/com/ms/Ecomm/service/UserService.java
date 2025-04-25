package com.ms.Ecomm.service;

import com.ms.Ecomm.model.User;
import com.ms.Ecomm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // want user repo here
    @Autowired
    private UserRepository userRepository;
    public User registerUser(User user) {
        try {

            // here by userRepo, one method will call name save()(builtIn - method()) of JPA
            // and it will take user details and Save into DataBase. &  holding it into newUser.
            User newUser = userRepository.save(user);
            System.out.println("User Added to DataBase");
            return newUser; // return newUser
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User loginUser(String email , String password) {

        // if user enters email & password
        // 1 st step will do that check in the DB if the user is present or not
        User user = userRepository.findByemail(email);

        // if we found user in DB & then will check if the password of  user
        // are equal to the DB user password then we will return user
        if(user != null && user.getPassword().equals(password)){
            return user; // returning user
        }

        // if the user not found in DB then return null
        return null; // invalid credential.
    }

    public List<User> getAllUsers() {

        // here userRepo is calling findAll() method of JPA
        // to show all details of users & also we are returning it.
        return userRepository.findAll();
    }
}
