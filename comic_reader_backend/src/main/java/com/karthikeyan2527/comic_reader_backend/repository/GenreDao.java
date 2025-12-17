package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends JpaRepository<Genre, Integer> {

    Optional<Genre> findByName(String name);
}