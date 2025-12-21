package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.JwtUtil;
import com.karthikeyan2527.comic_reader_backend.dto.AuthDTO;
import com.karthikeyan2527.comic_reader_backend.dto.HistoryItemDTO;
import com.karthikeyan2527.comic_reader_backend.dto.ReadingHistoryDTO;
import com.karthikeyan2527.comic_reader_backend.dto.UserDTO;
import com.karthikeyan2527.comic_reader_backend.entity.Chapter;
import com.karthikeyan2527.comic_reader_backend.entity.ReadingHistory;
import com.karthikeyan2527.comic_reader_backend.entity.User;
import com.karthikeyan2527.comic_reader_backend.repository.ChapterDao;
import com.karthikeyan2527.comic_reader_backend.repository.ComicDao;
import com.karthikeyan2527.comic_reader_backend.repository.ReadingHistoryDao;
import com.karthikeyan2527.comic_reader_backend.repository.UserDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ComicDao comicDao;

    @Autowired
    ChapterDao chapterDao;

    @Autowired
    ReadingHistoryDao readingHistoryDao;

    public Optional<String> signIn(AuthDTO authDTO){
        try {
            User user = modelMapper.map(authDTO, User.class);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            // TODO: Pass Authentication object to generateToken? Refactor bad code here
            user = userDao.findByEmail(user.getEmail()).get();

            String token = jwtUtil.generateToken(user);

            return Optional.of(token);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> signUp(AuthDTO authDTO){
        User user = modelMapper.map(authDTO, User.class);

        if(user.getEmail().isEmpty()) return Optional.empty(); // TODO: Differentiate Null user and user present failure, Return Enum?

        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());

        if(optionalUser.isPresent()) return Optional.empty();

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user = userDao.save(user);

        String token = jwtUtil.generateToken(user);

        return Optional.of(token);
    }

    public Optional<ReadingHistory> saveComicRead(String token, Integer chapterId) {
        Integer userId = jwtUtil.getUserIdFromToken(token);
        if(userId == null) return Optional.empty();

        Optional<User> optionalUser = userDao.findById(userId);
        if(optionalUser.isEmpty()) return Optional.empty();

        Optional<Chapter> optionalChapter =  chapterDao.findById(chapterId);
        if(optionalChapter.isEmpty()) return Optional.empty();

        Optional<ReadingHistory> optionalReadingHistory = readingHistoryDao.findAll().stream().filter((readingHistory )-> readingHistory.getUser().equals(optionalUser.get()) && readingHistory.getComic().equals(optionalChapter.get().getComic())).findFirst();

        ReadingHistory readingHistory = null;

        if(optionalReadingHistory.isPresent()){
            readingHistory = optionalReadingHistory.get();
            readingHistory.setReadTime(LocalDateTime.now());
        } else {
            readingHistory = new ReadingHistory(null, optionalUser.get(), optionalChapter.get().getComic(), LocalDateTime.now());
        }

        readingHistory = readingHistoryDao.save(readingHistory);

        return Optional.of(readingHistory); // TODO: return DTO
    }

    public Optional<ReadingHistoryDTO> getReadingHistory(String token) {
        Integer userId = jwtUtil.getUserIdFromToken(token);
        if(userId == null) return Optional.empty();

        Optional<User> optionalUser = userDao.findById(userId);
        if(optionalUser.isEmpty()) return Optional.empty();

        List<ReadingHistory> readingHistories = readingHistoryDao.findAll().stream().filter((readingHistory)-> readingHistory.getUser().equals(optionalUser.get())).toList();

        User user = optionalUser.get();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        List<HistoryItemDTO> historyItemDTOS = readingHistories.stream().map(readingHistory -> new HistoryItemDTO(readingHistory.getComic(), readingHistory.getReadTime())).toList();
        ReadingHistoryDTO readingHistoryDTO = new ReadingHistoryDTO(userDTO, historyItemDTOS);

        return Optional.of(readingHistoryDTO);
    }
}