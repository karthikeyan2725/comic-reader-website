package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.JwtUtil;
import com.karthikeyan2527.comic_reader_backend.dto.CommentDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentPostDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Comment;
import com.karthikeyan2527.comic_reader_backend.entity.User;
import com.karthikeyan2527.comic_reader_backend.repository.CommentDao;
import com.karthikeyan2527.comic_reader_backend.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public List<CommentDTO> getComicComments(Integer comicId){
        return commentDao.findAllByCommentTypeAndCommentEntityId("comic", comicId).stream()
                .map((comment)-> modelMapper.map(comment, CommentDTO.class)).toList();
    }

    public Optional<CommentDTO> saveComment(CommentPostDTO commentPostDTO){

        if(commentPostDTO.getToken() == null) return Optional.empty();

        Integer userId = jwtUtil.getUserIdFromToken(commentPostDTO.getToken());

        Optional<User> optionalUser = userDao.findById(userId);

        if(optionalUser.isEmpty()) return Optional.empty();

        Comment comment = modelMapper.map(commentPostDTO, Comment.class);
        if(comment.getCommentEntityId() == null) return Optional.empty();
        log.info("3");
        if(comment.getCommentType() == null) return Optional.empty();
        log.info("4");

        User user = optionalUser.get();

        comment.setId(null);
        comment.setUser(user);
        comment.setUpvote(0);
        comment.setDownvote(0);
        comment.setPublishedTime(LocalDateTime.now());

        comment = commentDao.save(comment);
        CommentDTO savedCommentDTO = modelMapper.map(comment, CommentDTO.class);

        return Optional.of(savedCommentDTO);
    }

    public List<CommentDTO> getChapterComments(Integer chapterId) {
        return commentDao.findAllByCommentTypeAndCommentEntityId("chapter", chapterId).stream()
                .map((comment)-> modelMapper.map(comment, CommentDTO.class)).toList();
    }
}