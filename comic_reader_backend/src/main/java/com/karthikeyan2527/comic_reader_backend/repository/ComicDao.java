package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicDao extends JpaRepository<Comic, Integer> {}