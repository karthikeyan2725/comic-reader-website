package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDTO {
    Integer id;

    String name;

    String author;

    String artist;

    Integer yearOfRelease;

    String language;

    String description;

    Integer chapterCount;

    List<ChapterDTO> chapters = new ArrayList<>();
}
