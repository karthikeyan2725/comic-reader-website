package com.karthikeyan2527.comic_reader_backend;

import com.karthikeyan2527.comic_reader_backend.entity.Comic;
import com.karthikeyan2527.comic_reader_backend.entity.Genre;
import com.karthikeyan2527.comic_reader_backend.repository.ChapterDao;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import com.karthikeyan2527.comic_reader_backend.repository.GenreDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InsertComicCommandLineRunner implements CommandLineRunner { // TODO: Remove this after completion

    @Autowired
    ComicDao comicDao;

    @Autowired
    ChapterDao chapterDao;

    @Autowired
    GenreDao genreDao;

    @Override
    public void run(String... args) throws Exception {

        // Insert Comic Into Table
//        List<Comic> comicList = List.of(
//            new Comic(null,
//                "Bakuman",
//                "Takeshi Obata",
//                "Takeshi Obata",
//                2008,
//                "English",
//                "When Moritaka Mashiro, a middle school student, forgets his notebook in class, he finds his classmate, Akito Takagi, who notes Mashiro's drawings. Takagi asks him to become a manga artist to his stories. However, Mashiro declines, citing his late manga artist uncle, who died from overworking. Takagi incites Mashiro to meet with Miho Azuki, Mashiro's crush, and tells her the two plan to become manga artists. In response, Azuki reveals her plans to be a voice actress. Mashiro proposes to her that they should both marry when Azuki becomes a voice actress for the anime adaptation of their manga. She accepts, but under the condition that they not meet face-to-face again until then. The two boys then start creating manga, under the pen name Muto Ashirogi, in hopes of getting serialized in Weekly Shōnen Jump. ",
//                0,
//                new ArrayList<>(),
//                "",
//                new ArrayList<>()
//            ),
//            new Comic(null,
//                    "Solo Leveling",
//                    "Chugong",
//                    "Chugong",
//                    2016,
//                    "English",
//                    "In a world where hunters—human warriors who possess supernatural abilities—must battle deadly monsters to protect all mankind from certain annihilation, a notoriously weak hunter named Sung Jin-woo finds himself in a seemingly endless struggle for survival. One day, after narrowly surviving an overwhelmingly powerful double dungeon that nearly wipes out his entire party, a mysterious program called the System chooses him as its sole player and in turn, gives him the unique ability to level up in strength. This is something no other hunter is able to do, as a hunter's abilities are set once they awaken. Jinwoo then sets out on a journey as he fights against all kinds of enemies, both man and monster, to discover the secrets of the dungeons and the true source of his powers. He soon discovers that he has been chosen to inherit the position of Shadow Monarch, essentially turning him into an immortal necromancer known as the \"King of the Dead\" who has absolute rule over the dead, created by a god while in the middle of the war between its two creations of light and dark, the Rulers and the Monarch. He is the only Monarch who fights to save humanity, as the other Monarchs, who are actually the leaders of all monsters, are trying to kill him and wipe out the humans.",
//                    0,
//                    new ArrayList<>(),
//                    "",
//                    new ArrayList<>()
//            ),
//            new Comic(null,
//                    "Attack On Titan",
//                    "Hajime Isayama",
//                    "Hajime Isayama",
//                    2009,
//                    "English",
//                    "Attack on Titan (Japanese: 進撃の巨人, Hepburn: Shingeki no Kyojin; lit. 'The Advancing Giant') is a Japanese manga series written and illustrated by Hajime Isayama. Set in a world where humanity is forced to live in cities surrounded by three enormous walls that protect them from gigantic man-eating humanoids referred to as Titans, the story follows Eren Yeager, an adolescent boy who vows to exterminate the Titans after they bring about the destruction of his hometown and the death of his mother. It was serialized in Kodansha's monthly magazine Bessatsu Shōnen Magazine from September 2009 to April 2021, with its chapters collected in 34 tankōbon volumes. ",
//                    0,
//                    new ArrayList<>(),
//                    "",
//                    new ArrayList<>()
//            ),
//            new Comic(null,
//                    "Sakamoto Days",
//                    "Tetsu Ōkawa",
//                    "Tetsu Ōkawa",
//                    2024,
//                    "English",
//                    "The story revolves around Taro Sakamoto, a retired legendary hitman who has settled into a quiet and mundane life as a family man. However, his peaceful life is disrupted when former enemies and colleagues from his hitman days come seeking revenge. To protect his family and loved ones, Sakamoto must use his exceptional combat skills to face off against a variety of adversaries while trying to maintain his ordinary facade. ",
//                    0,
//                    new ArrayList<>(),
//                    "",
//                    new ArrayList<>()
//            ),
//            new Comic(null,
//                    "Kaiju No 8",
//                    "Kizuku Watanabe",
//                    "Kizuku Watanabe",
//                    2024,
//                    "English",
//                    "The story follows Kafka Hibino who, after ingesting a parasitic creature, gains the ability to turn into a kaiju and now must navigate using his power while trying to become part of an organization that eliminates kaiju to fulfill a promise he made with a childhood friend. Matsumoto wrote the outline of the story of Kaiju No. 8 near the end of 2018 making it his second series for the magazine. The series was heavily influenced by Japanese tokusatsu media, especially Ultraman, while the author's struggles in the manga industry served as a basis for the main character's backstory.",
//                    0,
//                    new ArrayList<>(),
//                    "",
//                    new ArrayList<>()
//            ),
//            new Comic(null,
//                    "Naruto",
//                    "Kishimoto Masashi",
//                    "Kishimoto Masashi",
//                    1999,
//                    "English",
//                    "Before Naruto's birth, a great demon fox had attacked the Hidden Leaf Village. The 4th Hokage from the leaf village sealed the demon inside the newly born Naruto, causing him to unknowingly grow up detested by his fellow villagers. Despite his lack of talent in many areas of ninjutsu, Naruto strives for only one goal: to gain the title of Hokage, the strongest ninja in his village. Desiring the respect he never received, Naruto works toward his dream with fellow friends Sasuke and Sakura and mentor Kakashi as they go through many trials and battles that come with being a ninja.",
//                    0,
//                    new ArrayList<>(),
//                    "",
//                    new ArrayList<>()
//            )
//        );
//        for(Comic comic : comicList){
//            comicDao.save(comic);
//        }


        // Update URL of Comics
//        log.info("updating tables");
//        comicDao.saveAll(comicDao.findAll().stream().peek((comic)->comic.setCoverArtUrl("https://vnqpkstmadacbnlcvgax.supabase.co/storage/v1/object/public/comic-asia/cover_art/" + comic.getId() + ".jpg")).toList());

        // Creating Genres
//        List<String> genres = List.of("Action", "Adventure", "Comedy", "Ninja", "Shounen", "Friendship", "Dark Fantasy", "Post Apocalyptic", "Monster", "Sci-Fi", "Slice of Life", "Coming of age", "Drama", "Romance", "Cosmic Horror");
//        for(String genreString : genres){
//            genreDao.save(new Genre(null, genreString));
//        }

        // Updating Genres
//        List<Integer> narutoGenres = List.of(1, 2, 3, 4, 5, 6);
//        List<Integer> soloLevelingGenres = List.of(1, 15, 9, 16);
//        List<Integer> aotGenres = List.of(8, 9, 11);
//        List<Integer> sakamotoDays = List.of(1, 3, 6);
//        List<Integer> kaijuNo8 = List.of(1, 2, 3, 11, 10);
//        List<Integer> bakuman = List.of(3, 6, 14, 12, 13, 15);
//
//        comicDao.saveAll(
//                comicDao.findById(1957).stream()
//                        .peek( (comic) ->
//                                comic.setGenres(genreDao.findAllById(narutoGenres).stream().toList()))
//                        .toList()
//        );
//
//        comicDao.saveAll(
//                comicDao.findById(1952).stream()
//                        .peek( (comic) ->
//                                comic.setGenres(genreDao.findAllById(bakuman).stream().toList()))
//                        .toList()
//        );
//
//        comicDao.saveAll(
//                comicDao.findById(1953).stream()
//                        .peek( (comic) ->
//                                comic.setGenres(genreDao.findAllById(soloLevelingGenres).stream().toList()))
//                        .toList()
//        );
//
//        comicDao.saveAll(
//                comicDao.findById(1954).stream()
//                        .peek( (comic) ->
//                                comic.setGenres(genreDao.findAllById(aotGenres).stream().toList()))
//                        .toList()
//        );
//
//        comicDao.saveAll(
//                comicDao.findById(1955).stream()
//                        .peek( (comic) ->
//                                comic.setGenres(genreDao.findAllById(sakamotoDays).stream().toList()))
//                        .toList()
//        );
//
//        comicDao.saveAll(
//                comicDao.findById(1956).stream()
//                        .peek( (comic) ->
//                                comic.setGenres(genreDao.findAllById(kaijuNo8).stream().toList()))
//                        .toList()
//        );
    }
}
