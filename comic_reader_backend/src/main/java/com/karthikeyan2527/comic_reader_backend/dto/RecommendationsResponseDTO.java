package com.karthikeyan2527.comic_reader_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationsResponseDTO {

    @JsonProperty("valid_comic_ids")
    private List<Integer> validComicIds;

    @JsonProperty("recommendations")
    private List<ComicRecommendationDTO> recommendations;
}
