package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterDao extends JpaRepository<Chapter, Integer> {
}
