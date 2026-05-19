package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.dto.ComicDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ComicRecommendationDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ReadingHistoryDTO;
import com.karthikeyan2527.comic_reader_backend.dto.RecommendationsResponseDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecommenderService {

    @Autowired
    private UserService userService;

    @Autowired
    private ComicDao comicDao;

    @Autowired
    private RestClient restClient;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<List<ComicDTO>> recommendationsFromHistory(String token){
        Optional<ReadingHistoryDTO> optionalReadingHistoryDTO = userService.getReadingHistory(token);
        if(optionalReadingHistoryDTO.isEmpty()) return Optional.empty();

        ReadingHistoryDTO readingHistoryDTO = optionalReadingHistoryDTO.get();
        if(readingHistoryDTO.getHistory().isEmpty()) return Optional.empty(); // TODO: Some other way to get recommendations?

        String comicIds = readingHistoryDTO.getHistory().stream()
                .map(hDTO->hDTO.getComic().getId().toString())
                .map(s -> "comic_ids=" + s)
                .collect(Collectors.joining("&"));

        RecommendationsResponseDTO recommendationsResponseDTO = restClient.get()
                .uri("http://localhost:8000/recommendations?" + comicIds)
                .retrieve()
                .body(RecommendationsResponseDTO.class);

        List<ComicDTO> comicsDTOs = new ArrayList<>();

        for(ComicRecommendationDTO c : recommendationsResponseDTO.getRecommendations()) {
            if (c.getComicId() != null) {
                Optional<Comic> optionalComic = comicDao.findById(c.getComicId());
                if (optionalComic.isPresent()) comicsDTOs.add(modelMapper.map(optionalComic.get(), ComicDTO.class));
            }
        }

        return Optional.of(comicsDTOs);
    }
}
