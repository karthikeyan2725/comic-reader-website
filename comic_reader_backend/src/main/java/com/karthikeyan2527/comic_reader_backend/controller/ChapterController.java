package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentPostDTO;
import com.karthikeyan2527.comic_reader_backend.service.ChapterService;
import com.karthikeyan2527.comic_reader_backend.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @Autowired
    CommentService commentService;

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

    @GetMapping("/{chapter_id}/comments")
    ResponseEntity<List<CommentDTO>> getChapterComments(@PathVariable("chapter_id") Integer chapterId){
        List<CommentDTO> commentDTOS = commentService.getChapterComments(chapterId); // TODO : Chapter not found? what to do

        return ResponseEntity.ok(commentDTOS);
    }

    @PostMapping("/comment")
    ResponseEntity<?> saveChapterComment(@RequestBody CommentPostDTO commentPostDTO){ // TODO: Return Why Comment Failed Properly
        Optional<CommentDTO> optionalCommentDTO = commentService.saveComment(commentPostDTO);

        if(optionalCommentDTO.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}