package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreDao extends JpaRepository<Genre, Integer> {}
