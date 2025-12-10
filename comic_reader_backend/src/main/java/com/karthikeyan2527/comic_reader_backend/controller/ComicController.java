package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
