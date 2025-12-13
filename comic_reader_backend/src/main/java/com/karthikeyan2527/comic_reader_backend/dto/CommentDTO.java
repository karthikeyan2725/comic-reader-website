package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Integer id;

    private UserDTO user;

    private Integer commentEntityId;

    private String comment;

    private Integer upvote;

    private Integer downvote;

    private LocalDateTime publishedTime;
}