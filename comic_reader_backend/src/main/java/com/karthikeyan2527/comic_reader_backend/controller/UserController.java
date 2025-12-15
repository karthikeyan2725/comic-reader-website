package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.AuthDTO;
import com.karthikeyan2527.comic_reader_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    ResponseEntity<?> signInMethod(@RequestBody AuthDTO authDTO) {
        Optional<String> optionalToken = userService.signIn(authDTO);

        if(optionalToken.isPresent()) return ResponseEntity.ok(optionalToken.get());

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/sign-up")
    ResponseEntity<?> signUpMethod(@RequestBody AuthDTO authDTO){
        // TODO: Handle response for Conflict properly

        Optional<String> optionalToken = userService.signUp(authDTO);

        if(optionalToken.isPresent()) return ResponseEntity.ok(optionalToken.get());

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}