package com.karthikeyan2527.comic_reader_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicRecommendationDTO {

    @JsonProperty("comic_id")
    private Integer comicId;

    @JsonProperty("distance")
    private Float distance;
}
