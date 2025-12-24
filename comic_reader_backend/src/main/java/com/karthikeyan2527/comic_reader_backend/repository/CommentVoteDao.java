package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.Comment;
import com.karthikeyan2527.comic_reader_backend.entity.CommentVote;
import com.karthikeyan2527.comic_reader_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentVoteDao extends JpaRepository<CommentVote, Integer> {

    Optional<CommentVote> findByCommentAndUser(Comment comment, User user);
}