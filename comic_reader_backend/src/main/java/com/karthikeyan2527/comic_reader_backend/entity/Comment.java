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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user; // Convert To DTO in CommentDTO

    private String commentType; // TODO: Use Enum for comment type

    private Integer commentEntityId;

    @Column(columnDefinition = "text")
    private String comment;

    private Integer votes;

    private LocalDateTime publishedTime;
}