package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ComicDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentPostDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Comment;
import com.karthikeyan2527.comic_reader_backend.service.ComicService;
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
@RequestMapping("comic")
public class ComicController {

    @Autowired
    ComicService comicService;

    @Autowired
    CommentService commentService;

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

    @GetMapping("/{comic_id}/comments")
    ResponseEntity<List<CommentDTO>> getComicComments(@PathVariable("comic_id") Integer comicId){ // TODO: Handle comic not found with optional, Check again for consistency
        List<CommentDTO> comments = commentService.getComicComments(comicId);

        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comment")
    ResponseEntity<?> postComicComment(@RequestBody CommentPostDTO commentPostDTO){
        log.info(commentPostDTO.toString());
        Optional<CommentDTO> optionalCommentDTO = commentService.saveComment(commentPostDTO); // TODO: Should this return commentDTO?

        if(optionalCommentDTO.isPresent()) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}