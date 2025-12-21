package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveReadDTO {

    String token;

    Integer chapterId;
}