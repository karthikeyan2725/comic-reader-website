package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ComicDTO;
import com.karthikeyan2527.comic_reader_backend.service.ComicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:5173") // TODO: Make Global or use Application Yaml
@RestController
@RequestMapping("comic")
public class ComicController {

    @Autowired
    ComicService comicService;

    // TODO: Handle when 1) Comic not found 2) comic does not contain any chapter
    @GetMapping("/{comic_id}/chapters")
    ResponseEntity<List<ChapterDTO>> getComicChapters(@PathVariable("comic_id") Integer comicId){
        return comicService.getChaptersOfComic(comicId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{comic_id}")
    ResponseEntity<ComicDTO> getComicDetails(@PathVariable("comic_id") Integer comicId){
        return comicService.getComicDetail(comicId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}