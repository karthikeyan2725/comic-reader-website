package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterDao extends JpaRepository<Chapter, Integer> {

    Optional<Chapter> getChapterByComicAndChapterNumber(Comic comic, Integer chapterNumber);
}