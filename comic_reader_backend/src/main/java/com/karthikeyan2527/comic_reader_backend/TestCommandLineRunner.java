package com.karthikeyan2527.comic_reader_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class TestCommandLineRunner implements CommandLineRunner {
    @Autowired
    TestRepository testRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Spring works...");
    }
}