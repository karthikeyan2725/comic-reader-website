package com.karthikeyan2527.comic_reader_backend.dto;

import com.karthikeyan2527.comic_reader_backend.entity.Comment;
import com.karthikeyan2527.comic_reader_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVoteDTO {

    Integer id;

    Comment comment;

    User user;

    Integer vote;

    LocalDateTime voteTime;
}