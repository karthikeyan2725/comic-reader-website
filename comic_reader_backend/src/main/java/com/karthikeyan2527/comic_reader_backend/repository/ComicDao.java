package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComicDao extends JpaRepository<Comic, Integer> {

    List<Comic> findByNameContainingIgnoreCase(String query);
}