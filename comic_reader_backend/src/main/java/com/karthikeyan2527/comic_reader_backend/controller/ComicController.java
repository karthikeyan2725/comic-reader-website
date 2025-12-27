package com.karthikeyan2527.comic_reader_backend.controller;

import com.karthikeyan2527.comic_reader_backend.dto.*;
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

    @GetMapping("/popular")
    ResponseEntity<List<ComicDTO>> getPopularComics(){ // TODO: add query to get count and pagination
        List<ComicDTO> comicDTOS = comicService.getPopularComics();

        return ResponseEntity.ok(comicDTOS);
    }

    @GetMapping("/comics")
    ResponseEntity<List<ComicDTO>> getComics(@RequestParam("genre") String genre){ // TODO: Use pageable
        List<ComicDTO> comicDTOS = comicService.getComicsByGenre(genre);

        return ResponseEntity.ok(comicDTOS);
    }

    @GetMapping("/{comic_id}/comments")
    ResponseEntity<List<CommentDTO>> getComicComments(@PathVariable("comic_id") Integer comicId, @RequestParam(value = "token", defaultValue = "") String token){ // TODO: Handle comic not found with optional, Check again for consistency
        List<CommentDTO> comments = commentService.getComicComments(comicId, token);

        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comment")
    ResponseEntity<?> postComicComment(@RequestBody CommentPostDTO commentPostDTO){
        log.info(commentPostDTO.toString());
        Optional<CommentDTO> optionalCommentDTO = commentService.saveComment(commentPostDTO); // TODO: Should this return commentDTO?

        if(optionalCommentDTO.isPresent()) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search")
    ResponseEntity<?> searchQuery(@RequestParam("query") String query){

        List<ComicDTO> comicDTOS = comicService.search(query);

        return ResponseEntity.ok(comicDTOS);
    }

    @PostMapping("/comment/{comment_id}/vote")
    ResponseEntity<?> voteComicComment(@PathVariable("comment_id") Integer commentId, @RequestParam("vote") Integer vote, @RequestParam(value = "token") String token){
        Optional<CommentVoteDTO> optionalCommentVoteDTO = commentService.voteComment(vote, commentId, token);

        if(optionalCommentVoteDTO.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/advanced-search")
    ResponseEntity<?> advancedSearch(AdvancedSearchDTO advancedSearchDTO){
        List<ComicDTO> resultComics = comicService.doAdvancedSearch(advancedSearchDTO);

        return ResponseEntity.ok(resultComics);
    }
}