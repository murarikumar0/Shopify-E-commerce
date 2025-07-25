package com.ms.Ecomm.service;

import com.ms.Ecomm.model.User;
import com.ms.Ecomm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    // loading User
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // user is coming from DB
        User user = userRepository.findByEmail(username);

       if(user == null){
           throw   new UsernameNotFoundException("User Not Found");
       }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // default role
        );


    }
}
