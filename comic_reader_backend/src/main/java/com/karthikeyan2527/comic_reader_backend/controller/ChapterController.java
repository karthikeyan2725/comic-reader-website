package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    // TODO : Return Reason for error (Chapter not found)
    @GetMapping("/{chapter_id}")
    ResponseEntity<ChapterDTO> getChapterDetails(@PathVariable("chapter_id") Integer chapterId){
        return chapterService.getChapterDetails(chapterId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // TODO: Return if comic does not exist or conflict with existing chapter when Error
    @PostMapping
    ResponseEntity<ChapterDTO> postChapterDetails(@RequestBody ChapterDTO chapterDTO){
        return chapterService.saveChapterDetails(chapterDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}