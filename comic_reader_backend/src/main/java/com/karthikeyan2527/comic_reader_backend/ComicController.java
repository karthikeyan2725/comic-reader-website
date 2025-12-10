package com.karthikeyan2527.comic_reader_backend;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("comic")
public class ComicController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ComicDao comicDao;

    @GetMapping("/{comic_id}/chapters")
    ResponseEntity<List<ChapterDTO>> getComicChapters(@PathVariable("comic_id") Integer comicId){
        Optional<Comic> optionalComic = comicDao.findById(comicId);
        if(optionalComic.isPresent()){
            List<Chapter> chapters = optionalComic.get().getChapters();
            List<ChapterDTO> chapterDTOS = chapters.stream()
                    .map((chapter) -> modelMapper.map(chapter, ChapterDTO.class))
                    .peek((chapterDTO -> chapterDTO.setComicId(comicId)))
                    .sorted((c1, c2)->Integer.compare(c1.getChapterNumber(), c2.getChapterNumber()))
                    .toList();
            return ResponseEntity.ok(chapterDTOS);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
