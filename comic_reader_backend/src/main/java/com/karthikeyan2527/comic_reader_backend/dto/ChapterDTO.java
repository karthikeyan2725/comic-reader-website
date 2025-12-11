package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDTO {
    private Integer id;

    private Integer comicId;

    private String comicName;

    private Integer chapterNumber;

    private LocalDateTime publishedTime;

    private Integer readCount;

    private Integer pages;

    private String chapterLink;

    private Integer prevChapterId;

    private Integer nextChapterId;
}
