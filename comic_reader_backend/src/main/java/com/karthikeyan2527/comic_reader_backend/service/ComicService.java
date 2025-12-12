package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ComicDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComicService {

    @Autowired
    private ComicDao comicDao;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<List<ChapterDTO>> getChaptersOfComic(Integer comicId){
        Optional<Comic> optionalComic = comicDao.findById(comicId);

        if(optionalComic.isEmpty()) return Optional.empty();

        List<Chapter> chapters = optionalComic.get().getChapters();
        List<ChapterDTO> chapterDTOS = chapters.stream()
                .map((chapter) -> modelMapper.map(chapter, ChapterDTO.class))
                .peek((chapterDTO -> chapterDTO.setComicId(comicId)))
                .sorted((c1, c2)->Integer.compare(c1.getChapterNumber(), c2.getChapterNumber()))
                .toList();

        return Optional.of(chapterDTOS);
    }

    public Optional<ComicDTO> getComicDetail(Integer comicId){
        Optional<Comic> optionalComic = comicDao.findById(comicId);

        if(optionalComic.isEmpty()) return Optional.empty();

        Comic comic = optionalComic.get();
        ComicDTO comicDTO = modelMapper.map(comic, ComicDTO.class);

        return Optional.of(comicDTO);
    }
}