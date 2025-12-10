package com.karthikeyan2527.comic_reader_backend;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import com.karthikeyan2527.comic_reader_backend.repository.ChapterDao;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@CrossOrigin("http://localhost:5173/")
@RestController
@RequestMapping("chapter")
public class ChapterController {

    @Autowired
    ComicDao comicDao;

    @Autowired
    ChapterDao chapterDao;

    @Autowired
    ModelMapper modelMapper;

    String baseStorageUrl = "https://vnqpkstmadacbnlcvgax.supabase.co/storage/v1/object/public/comic-asia/";

    @GetMapping("/{chapter_id}")
    ResponseEntity<ChapterDTO> getChapterDetails(@PathVariable("chapter_id") Integer chapterId){
        Optional<Chapter> optionalChapter = chapterDao.findById(chapterId);
        log.info("Loading chapter " + chapterId);
        if(optionalChapter.isPresent()){
            Chapter chapter = optionalChapter.get();
            ChapterDTO chapterDTO = modelMapper.map(chapter, ChapterDTO.class);
            chapterDTO.setComicId(chapter.getComic().getId());
            chapterDTO.setComicName(chapter.getComic().getName());

            Comic comic = chapter.getComic();

            Integer chapterNumber = chapter.getChapterNumber();
            Optional<Chapter> optionalPrevChapter = comic.getChapters().stream().filter((ch)->ch.getChapterNumber()== chapterNumber-1).findFirst();
            if(optionalPrevChapter.isPresent())
                chapterDTO.setPrevChapterId(optionalPrevChapter.get().getId());

            Optional<Chapter> optionalNextChapter = comic.getChapters().stream().filter((ch)->ch.getChapterNumber()== chapterNumber+1).findFirst();
            if(optionalNextChapter.isPresent())
                chapterDTO.setNextChapterId(optionalNextChapter.get().getId());

            return new ResponseEntity<>(chapterDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    ResponseEntity<ChapterDTO> postChapterDetails(@RequestBody ChapterDTO chapterDTO){
        Optional<Comic> optionalComic = comicDao.findById(chapterDTO.getComicId());

        if(optionalComic.isEmpty()) return new ResponseEntity<>(HttpStatus.CONFLICT);

        Comic comic = optionalComic.get();
        Chapter chapter = modelMapper.map(chapterDTO, Chapter.class);
        chapter.setComic(comic);
        chapter.setPublishedTime(LocalDateTime.now());
        chapter.setReadCount(0);

        chapter = chapterDao.save(chapter);
        chapter.setChapterLink(baseStorageUrl + chapter.getId().toString());
        chapter = chapterDao.save(chapter);

        comic.setChapterCount(comic.getChapterCount() + 1);
        chapterDTO = modelMapper.map(chapter, ChapterDTO.class);
        chapterDTO.setComicId(chapter.getComic().getId());
        chapterDTO.setComicName(chapter.getComic().getName());

        return new ResponseEntity<>(chapterDTO, HttpStatus.OK);
    }
}
