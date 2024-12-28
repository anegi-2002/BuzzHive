package com.socialMedia.BuzzHive.service;

import com.socialMedia.BuzzHive.exception.UserNotFoundException;
import com.socialMedia.BuzzHive.userRegistration.model.Users;
import com.socialMedia.BuzzHive.userRegistration.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTService JWTService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<Users> register(Users user) throws UserNotFoundException {
        Users userO = repo.findByUsername(user.getUsername());
        if(userO!=null)
            throw new UserNotFoundException("User already exists");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setUser_id(UUID.randomUUID().toString());
        return new ResponseEntity(repo.save(user), HttpStatus.ACCEPTED);
    }

    public Map<String,Object> verify(Users user) {
        try {
            Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                return JWTService.generateToken(user.getUsername(),authentication);
            }
            return Map.of("token", "failed");
        } catch (AuthenticationException e) {
            return Map.of("token", "failed");
        } catch (Exception e) {
            return Map.of("token", "failed");
        }
    }
    public boolean isUserPresntInDb(String userId) throws UserNotFoundException {
//        Users users = repo.findByUserID(userId);
//        if(Objects.isNull(users))
//            throw new UsernotFoundException("User not found");
        return true;
    }
}
