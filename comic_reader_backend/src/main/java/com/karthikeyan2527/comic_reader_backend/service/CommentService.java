package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.dto.CommentDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Comment;
import com.karthikeyan2527.comic_reader_backend.repository.CommentDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    ModelMapper modelMapper;

    public List<CommentDTO> getComicComments(Integer comicId){
        return commentDao.findAllByCommentTypeAndCommentEntityId("comic", comicId).stream()
                .map((comment)-> modelMapper.map(comment, CommentDTO.class)).toList();
    }

    public Optional<CommentDTO> saveComicComment(CommentDTO commentDTO){
        Comment comment = modelMapper.map(commentDTO, Comment.class);

        if(comment.getUser().getId() == null) return Optional.empty();
        if(comment.getCommentEntityId() == null) return Optional.empty();

        comment.setId(null);
        comment.setUpvote(0);
        comment.setDownvote(0);
        comment.setPublishedTime(LocalDateTime.now());
        comment.setCommentType("comic"); // TODO: Add enum for comment Type

        comment = commentDao.save(comment);
        CommentDTO savedCommentDTO = modelMapper.map(comment, CommentDTO.class);

        return Optional.of(savedCommentDTO);
    }
}