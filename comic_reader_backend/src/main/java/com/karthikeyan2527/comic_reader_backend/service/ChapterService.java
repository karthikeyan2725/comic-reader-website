package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import com.karthikeyan2527.comic_reader_backend.repository.ChapterDao;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChapterService { // TODO : Check if methods could be simplified.

    @Autowired
    private ComicDao comicDao;

    @Autowired
    private ChapterDao chapterDao;

    @Autowired
    private ModelMapper modelMapper;

    private String baseStorageUrl = "https://vnqpkstmadacbnlcvgax.supabase.co/storage/v1/object/public/comic-asia/"; // TODO : Move to application.yaml

    public Optional<ChapterDTO> getChapterDetails(Integer chapterId){
        Optional<Chapter> optionalChapter = chapterDao.findById(chapterId);

        if(optionalChapter.isEmpty()) return Optional.empty();

        Chapter chapter = optionalChapter.get();
        Comic comic = chapter.getComic();
        Optional<Chapter> optionalPrevChapter = chapterDao.getChapterByComicAndChapterNumber(comic, chapter.getChapterNumber()-1);
        Optional<Chapter> optionalNextChapter = chapterDao.getChapterByComicAndChapterNumber(comic, chapter.getChapterNumber()+1);

        ChapterDTO chapterDTO = modelMapper.map(chapter, ChapterDTO.class);
        chapterDTO.setComicId(chapter.getComic().getId());
        chapterDTO.setComicName(chapter.getComic().getName());
        if(optionalPrevChapter.isPresent())
            chapterDTO.setPrevChapterId(optionalPrevChapter.get().getId());
        if(optionalNextChapter.isPresent())
            chapterDTO.setNextChapterId(optionalNextChapter.get().getId());

        return Optional.of(chapterDTO);
    }

    public Optional<ChapterDTO> saveChapterDetails(ChapterDTO chapterDTO){
        Optional<Comic> optionalComic = comicDao.findById(chapterDTO.getComicId());

        if(optionalComic.isEmpty()) return Optional.empty();

        Comic comic = optionalComic.get();
        Chapter chapter = modelMapper.map(chapterDTO, Chapter.class);

        chapter.setId(null);
        chapter.setComic(comic);
        chapter.setPublishedTime(LocalDateTime.now());
        chapter.setReadCount(0);
        chapter = chapterDao.save(chapter);

        String chapterUrl = baseStorageUrl + chapter.getId().toString();
        chapter.setChapterLink(chapterUrl);
        chapter = chapterDao.save(chapter);

        comic.setChapterCount(comic.getChapterCount() + 1);
        comicDao.save(comic);

        ChapterDTO savedChapterDTO = modelMapper.map(chapter, ChapterDTO.class);
        savedChapterDTO.setComicId(chapter.getComic().getId());
        savedChapterDTO.setComicName(chapter.getComic().getName());

        return Optional.of(savedChapterDTO);
    }
}