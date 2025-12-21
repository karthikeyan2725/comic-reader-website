package com.karthikeyan2527.comic_reader_backend.repository;

import com.karthikeyan2527.comic_reader_backend.entity.ReadingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingHistoryDao extends JpaRepository<ReadingHistory, Integer> {}
