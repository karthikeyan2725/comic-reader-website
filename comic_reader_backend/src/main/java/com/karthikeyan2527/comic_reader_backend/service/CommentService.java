package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.dto.CommentDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentPostDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Comment;
import com.karthikeyan2527.comic_reader_backend.entity.User;
import com.karthikeyan2527.comic_reader_backend.repository.CommentDao;
import com.karthikeyan2527.comic_reader_backend.repository.UserDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ModelMapper modelMapper;

    public List<CommentDTO> getComicComments(Integer comicId){
        return commentDao.findAllByCommentTypeAndCommentEntityId("comic", comicId).stream()
                .map((comment)-> modelMapper.map(comment, CommentDTO.class)).toList();
    }

    public Optional<CommentDTO> saveComment(CommentPostDTO commentPostDTO){

        if(commentPostDTO.getToken() == null) return Optional.empty();
        log.info("1");
        byte[] secret = Base64.getDecoder().decode("SGVsbG9UaGlzSXNDb21pY1JlYWRlcldlYnNpdGVNYWRlV2l0aExvdmVCeUthcnRoaWtleWFuQW5kVGhpc0lzVXNlZFRvU2lnblRoZUhhc2hPZkpXVA==");

        Claims result = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret)).build().parseSignedClaims(commentPostDTO.getToken()).getPayload();

        log.info(result.toString());

        Optional<User> optionalUser = userDao.findById(Integer.valueOf(result.getSubject()));

        if(optionalUser.isEmpty()) return Optional.empty();

        Comment comment = modelMapper.map(commentPostDTO, Comment.class);
        if(comment.getCommentEntityId() == null) return Optional.empty();
        log.info("3");
        if(comment.getCommentType() == null) return Optional.empty();
        log.info("4");

        User user = optionalUser.get();

        // TODO: REfactor
        comment.setId(null);
        comment.setUser(user);
        comment.setUpvote(0);
        comment.setDownvote(0);
        comment.setPublishedTime(LocalDateTime.now());
//        comment.setCommentType("comic"); // TODO: Add enum for comment Type

        comment = commentDao.save(comment);
        CommentDTO savedCommentDTO = modelMapper.map(comment, CommentDTO.class);

        return Optional.of(savedCommentDTO);
    }
}