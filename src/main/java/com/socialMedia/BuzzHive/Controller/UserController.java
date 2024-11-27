package com.socialMedia.BuzzHive.Controller;

import com.socialMedia.BuzzHive.model.Users;
import com.socialMedia.BuzzHive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
   private UserService service;


    @PostMapping("/register")
    public String register(@RequestBody Users endUser){
        System.out.println("request register"+endUser);
        return service.register(endUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody  Users user)
    {
        return service.verify(user);
    }

}
