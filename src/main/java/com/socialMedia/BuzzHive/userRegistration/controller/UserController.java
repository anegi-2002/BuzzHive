package com.socialMedia.BuzzHive.userRegistration.controller;

import com.socialMedia.BuzzHive.exception.UserNotFoundException;
import com.socialMedia.BuzzHive.service.UserService;
import com.socialMedia.BuzzHive.userRegistration.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users endUser) throws UserNotFoundException {
        return service.register(endUser);
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody  Users user)
    {
        return service.verify(user);
    }

}
