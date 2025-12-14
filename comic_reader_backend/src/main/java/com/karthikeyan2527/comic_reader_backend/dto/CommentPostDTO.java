package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostDTO { // TODO: Check if this could be modified (CommentDTO already Exists?)

    String token;

    String commentType; // TODO: Remove type by using Sequence for Chapter and comic ID? cha12091 and com1234?

    Integer commentEntityId;

    String comment;
}
