package com.karthikeyan2527.comic_reader_backend.service;

import com.karthikeyan2527.comic_reader_backend.JwtUtil;
import com.karthikeyan2527.comic_reader_backend.dto.AuthDTO;
import com.karthikeyan2527.comic_reader_backend.entity.User;
import com.karthikeyan2527.comic_reader_backend.repository.UserDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}