package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDTO {
    Integer id;

    Integer comicId;

    String comicName;

    Integer chapterNumber;

    LocalDateTime publishedTime;

    Integer readCount;

    Integer pages;

    String chapterLink;

    Integer prevChapterId;

    Integer nextChapterId;
}
