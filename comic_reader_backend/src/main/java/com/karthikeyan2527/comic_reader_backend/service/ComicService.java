package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.dto.AdvancedSearchDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ChapterDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ComicDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import com.karthikeyan2527.comic_reader_backend.entity.Genre;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import com.karthikeyan2527.comic_reader_backend.repository.GenreDao;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ComicService {

    @Autowired
    private ComicDao comicDao;

    @Autowired
    private GenreDao genreDao;

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

    public List<ComicDTO> getPopularComics() { // TODO: Write real logic to get popular comics
        List<Comic> comics = comicDao.findAll().subList(0, 5);

        List<ComicDTO> comicsDTOS = comics.stream().map(comic -> modelMapper.map(comic, ComicDTO.class)).toList();

        return comicsDTOS;
    }

    public List<ComicDTO> getComicsByGenre(String genre) {
        Optional<Genre> optionalGenre = genreDao.findByName(genre); // TODO: Handle if multiple genres with similar name, (Action, action)?

        if(optionalGenre.isEmpty()) return new ArrayList<>();

        Genre genreEntity = optionalGenre.get();
        List<ComicDTO> comicsDTOS = comicDao.findAll().stream()
                .filter(comic -> comic.getGenres().contains(genreEntity))
                .map(comic -> modelMapper.map(comic, ComicDTO.class))
                .toList();

        return comicsDTOS;
    }

    public List<ComicDTO> search(String query) {
        List<Comic> comics = comicDao.findByNameContainingIgnoreCase(query);
        List<ComicDTO> comicDTOS = comics.stream().map((comic) -> modelMapper.map(comic, ComicDTO.class)).toList();

        return comicDTOS;
    }

    public List<ComicDTO> doAdvancedSearch(AdvancedSearchDTO advancedSearchDTO) { // TODO: Handle genre, Sort error

        // TODO: !!!! REFACTOR ASAP

        List<String> genresString = (advancedSearchDTO.getGenres() != null) ? advancedSearchDTO.getGenres(): List.of();
        List<Genre> genres = genresString.stream().map((genreString) -> genreDao.findByName(genreString))
                .filter((optionalGenre)-> optionalGenre.isPresent())
                .map((optionalGenre)->optionalGenre.get())
                .toList();

        String query = advancedSearchDTO.getQuery();
        Integer before = advancedSearchDTO.getBefore();
        Integer after = advancedSearchDTO.getAfter();
        List<String> languages = advancedSearchDTO.getLanguages();

        Comparator<Comic> sortFunction = (c1, c2) -> c1.getName().compareTo(c2.getName());

        if(!advancedSearchDTO.getSortBy().isEmpty()){ // TODO: Add Ratings sort, Last Chapter Update
            String sort = advancedSearchDTO.getSortBy();
            if(sort.equals("Comic Name (ASC)")){
                sortFunction = (c1, c2) -> c1.getName().compareTo(c2.getName());
            } else if (sort.equals("Comic Name (DESC)")){
                sortFunction = (c1, c2) -> -c1.getName().compareTo(c2.getName());
            } else if (sort.equals("Publication (ASC)")){
                sortFunction = (c1, c2) -> Integer.compare(c1.getYearOfRelease(), c2.getYearOfRelease());
            } else if (sort.equals("Publication (DESC)")){
                sortFunction = (c1, c2) -> -Integer.compare(c1.getYearOfRelease(), c2.getYearOfRelease());
            }
        }

        List<ComicDTO> comicDTOS = comicDao.findAll().stream()
                .filter(comic-> (query==null || query.isBlank() || comic.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))))
                .filter(comic -> genres.isEmpty() || hasIntersection(comic.getGenres(), genres))
                .filter(comic-> (after==null || after<=comic.getYearOfRelease()) && ( before == null || comic.getYearOfRelease() <=before))
                .filter(comic -> languages==null || languages.contains(comic.getLanguage()))
                .sorted(sortFunction)
                .map(comic -> modelMapper.map(comic, ComicDTO.class))
                .toList();

        return comicDTOS;
    }

    public <T> boolean hasIntersection(List<T> l1, List<T> l2){
        for(T i1 : l1){
            if(l2.contains(i1)) return true;
        }
        return false;
    }
}