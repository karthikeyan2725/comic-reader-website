package com.karthikeyan2527.comic_reader_backend.dto;

import com.karthikeyan2527.comic_reader_backend.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDTO {
    private Integer id;

    private String name;

    private String author;

    private String artist;

    private Integer yearOfRelease;

    private String language;

    private String description;

    private Integer chapterCount;

    private String coverArtUrl;

    private List<Genre> genres;

    private List<ChapterDTO> chapters = new ArrayList<>();
}
