package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.AuthDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ReadingHistoryDTO;
import com.karthikeyan2527.comic_reader_backend.dto.SaveReadDTO;
import com.karthikeyan2527.comic_reader_backend.entity.ReadingHistory;
import com.karthikeyan2527.comic_reader_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    ResponseEntity<?> signUpMethod(@RequestBody AuthDTO authDTO) {
        // TODO: Handle response for Conflict properly

        Optional<String> optionalToken = userService.signUp(authDTO);

        if (optionalToken.isPresent()) return ResponseEntity.ok(optionalToken.get());

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/read")
    ResponseEntity<?> saveReadComic(@RequestBody SaveReadDTO saveReadDTO){
        Optional<ReadingHistory> optionalReadingHistory = userService.saveComicRead(saveReadDTO.getToken(), saveReadDTO.getChapterId());

        if(optionalReadingHistory.isPresent()) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/read/history")
    ResponseEntity<?> getReadingHistory(@RequestParam("token") String token){
        Optional<ReadingHistoryDTO> readingHistory = userService.getReadingHistory(token);

        if(readingHistory.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(readingHistory);
    }
}