package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.ComicDTO;
import com.karthikeyan2527.comic_reader_backend.service.RecommenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("recommend")
public class RecommendationController {

    @Autowired
    private RecommenderService recommenderService;

    @GetMapping("/comics")
    ResponseEntity<List<ComicDTO>> getComicRecommendations(@RequestParam("token") String token){
        Optional<List<ComicDTO>> optionalComicDTO = recommenderService.recommendationsFromHistory(token);
        if(optionalComicDTO.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND); // not valid user / no history avail
        return new ResponseEntity<>(optionalComicDTO.get(), HttpStatus.OK);
    }
}
