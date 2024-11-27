package com.socialMedia.BuzzHive.service;

import com.socialMedia.BuzzHive.model.Users;
import com.socialMedia.BuzzHive.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTService JWTService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register(Users users) {
        users.setPassword(encoder.encode(users.getPassword()));
        repo.save(users);
        return "success";
    }



    public String verify(Users user) {
        try {
            Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                return JWTService.generateToken(user.getUsername());
            } else {
                return "Fail";
            }

        } catch (AuthenticationException e) {
            return "Authentication failed: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
