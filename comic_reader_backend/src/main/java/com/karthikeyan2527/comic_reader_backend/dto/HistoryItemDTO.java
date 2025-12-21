package com.karthikeyan2527.comic_reader_backend.dto;

import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryItemDTO {

    Comic comic;

    LocalDateTime readTime;
}