package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.JwtUtil;
import com.karthikeyan2527.comic_reader_backend.dto.CommentDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentPostDTO;
import com.karthikeyan2527.comic_reader_backend.dto.CommentVoteDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Comment;
import com.karthikeyan2527.comic_reader_backend.entity.CommentVote;
import com.karthikeyan2527.comic_reader_backend.entity.User;
import com.karthikeyan2527.comic_reader_backend.repository.CommentDao;
import com.karthikeyan2527.comic_reader_backend.repository.CommentVoteDao;
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
    private CommentVoteDao commentVoteDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public List<CommentDTO> getComicComments(Integer comicId, String token){
        List<Comment> comments = commentDao.findAllByCommentTypeAndCommentEntityId("comic", comicId);
        List<CommentDTO> commentDTOS = null;

        if(!token.isEmpty()) {
            Optional<User> optionalUser = userDao.findById(jwtUtil.getUserIdFromToken(token));
            if(optionalUser.isEmpty()) return comments.stream().map((comment)->modelMapper.map(comment, CommentDTO.class)).toList();
            User user = optionalUser.get();
            commentDTOS = comments.stream().map((comment -> {
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                Optional<CommentVote> optionalCommentVote = commentVoteDao.findByCommentAndUser(comment, user);
                if(optionalCommentVote.isPresent()) commentDTO.setCommentVote(modelMapper.map(optionalCommentVote.get(), CommentVoteDTO.class));
                return commentDTO;
            })).toList();
        } else {
            commentDTOS = comments.stream().map((comment)->modelMapper.map(comment, CommentDTO.class)).toList();
        }

        return commentDTOS;
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
        comment.setVotes(0);
        comment.setPublishedTime(LocalDateTime.now());

        comment = commentDao.save(comment);
        CommentDTO savedCommentDTO = modelMapper.map(comment, CommentDTO.class);

        return Optional.of(savedCommentDTO);
    }

    public List<CommentDTO> getChapterComments(Integer chapterId, String token) {
        List<Comment> comments = commentDao.findAllByCommentTypeAndCommentEntityId("chapter", chapterId);
        List<CommentDTO> commentDTOS = null;

        if(!token.isEmpty()) {
            Optional<User> optionalUser = userDao.findById(jwtUtil.getUserIdFromToken(token));
            if(optionalUser.isEmpty()) return comments.stream().map((comment)->modelMapper.map(comment, CommentDTO.class)).toList();
            User user = optionalUser.get();
            commentDTOS = comments.stream().map((comment -> {
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                Optional<CommentVote> optionalCommentVote = commentVoteDao.findByCommentAndUser(comment, user);
                if(optionalCommentVote.isPresent()) commentDTO.setCommentVote(modelMapper.map(optionalCommentVote.get(), CommentVoteDTO.class));
                return commentDTO;
            })).toList();
        } else {
            commentDTOS = comments.stream().map((comment)->modelMapper.map(comment, CommentDTO.class)).toList();
        }

        return commentDTOS;
    }

    public Optional<CommentVoteDTO> voteComment(Integer vote, Integer commentId, String token){ // TODO: handle error response

        Integer tokenUserId = jwtUtil.getUserIdFromToken(token);
        Optional<User> optionalUser = userDao.findById(tokenUserId);
        if(optionalUser.isEmpty()) return Optional.empty();

        Optional<Comment> optionalComment = commentDao.findById(commentId);
        if(optionalComment.isEmpty()) return Optional.empty();

        if(vote != 1  && vote != -1) return Optional.empty();

        Comment comment = optionalComment.get();
        User user = optionalUser.get();
        Optional<CommentVote> optionalCommentVote = commentVoteDao.findByCommentAndUser(comment, user);

        CommentVote commentVote = null;
        if(optionalCommentVote.isEmpty()) {
            commentVote = new CommentVote(null, comment, user, vote, LocalDateTime.now());
            commentVote = commentVoteDao.save(commentVote);
            comment.setVotes(comment.getVotes() + vote);
        } else {
            commentVote = optionalCommentVote.get();
            if(commentVote.getVote() == vote.intValue()) {
                commentVoteDao.delete(commentVote); // TODO: How to handle return for delete ?
                comment.setVotes(comment.getVotes() - vote);
            } else {
                commentVote.setVoteTime(LocalDateTime.now());
                commentVote.setVote(vote);
                commentVote = commentVoteDao.save(commentVote);
                comment.setVotes(comment.getVotes() + 2 * vote);
            }
        }

        commentDao.save(comment);
        return Optional.of(modelMapper.map(commentVote, CommentVoteDTO.class));
    }
}