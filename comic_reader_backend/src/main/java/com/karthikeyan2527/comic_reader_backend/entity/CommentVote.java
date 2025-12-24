package com.karthikeyan2527.comic_reader_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVote {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Integer id;

    @ManyToOne
    Comment comment;

    @ManyToOne
    User user;

    Integer vote;

    LocalDateTime voteTime;
}