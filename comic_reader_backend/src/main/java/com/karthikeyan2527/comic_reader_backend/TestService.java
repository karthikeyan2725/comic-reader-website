package com.karthikeyan2527.comic_reader_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;

    String giveMeString(){
        return testRepository.findAll().toString();
    }
}
