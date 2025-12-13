package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.entity.User;
import com.karthikeyan2527.comic_reader_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("http://localhost:5173") // TODO: Make Global or use Application Yaml
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    ResponseEntity<?> signInMethod(@RequestBody User user) { // TODO: Convert to User/Login DTO
        Boolean isSignedIn = userService.signIn(user);

        if(isSignedIn) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/sign-up")
    ResponseEntity<?> signUpMethod(@RequestBody User user){ // TODO: Convert to User/Login DTO
        // TODO: Handle response for Conflict properly

        Boolean isSignedUp = userService.signUp(user);

        if(isSignedUp) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}