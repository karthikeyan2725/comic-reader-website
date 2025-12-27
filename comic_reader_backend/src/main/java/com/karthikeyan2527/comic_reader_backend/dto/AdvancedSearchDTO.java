package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedSearchDTO {

    String query;

    List<String> genres;

    String sortBy;

    Integer after;

    Integer before;

    List<String> languages;
}